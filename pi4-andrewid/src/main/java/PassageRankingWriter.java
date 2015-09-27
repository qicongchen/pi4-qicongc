import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.NonEmptyFSList;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;

import type.InputDocument;
import type.Passage;
import type.Question;
import type.SourceDocumentInformation;

/**
 * This CAS Consumer serves as a writer to generate your output. This is just template code, so you
 * need to implement actual code.
 */
public class PassageRankingWriter extends CasConsumer_ImplBase {

	 /**
	   * Name of configuration parameter that must be set to the path of a directory into which the
	   * output files will be written.
	   */
	  public static final String PARAM_OUTPUTDIR = "OutputDir";

	  private File mOutputDir;

	  private int mDocNum;

	  public void initialize() throws ResourceInitializationException {
	    mDocNum = 0;
	    mOutputDir = new File((String) getConfigParameterValue(PARAM_OUTPUTDIR));
	    if (!mOutputDir.exists()) {
	      mOutputDir.mkdirs();
	    }
	  }

	  /**
	   * Processes the CasContainer which was populated by the TextAnalysisEngines. <br>
	   * In this case, the CAS is converted to XML and written into the output file .
	   * 
	   * @param aCAS
	   *          CasContainer which has been populated by the TAEs
	   * 
	   * @throws ResourceProcessException
	   *           if there is an error in processing the Resource
	   * 
	   * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(org.apache.uima.cas.CAS)
	   */
	  public void processCas(CAS aCAS) throws ResourceProcessException {
	    JCas jcas;
	    try {
	      jcas = aCAS.getJCas();
	    } catch (CASException e) {
	      throw new ResourceProcessException(e);
	    }

	    // retrieve the filename of the input file from the CAS
	    FSIterator it = jcas.getAnnotationIndex(SourceDocumentInformation.type).iterator();
	    File outFile = null;
	    if (it.hasNext()) {
	      SourceDocumentInformation fileLoc = (SourceDocumentInformation) it.next();
	      File inFile;
	      try {
	        inFile = new File(new URL(fileLoc.getUri()).getPath());
	        String outFileName = "passageRanking.txt";
	        if (fileLoc.getOffsetInSource() > 0) {
	          outFileName += fileLoc.getOffsetInSource();
	        }
	        outFile = new File(mOutputDir, outFileName);
	      } catch (MalformedURLException e1) {
	        // invalid URL, use default processing below
	      }
	    }
	    if (outFile == null) {
	      outFile = new File(mOutputDir, "doc" + mDocNum++);
	    }
	    
	    FSIndex inputDocumentIndex = jcas.getAnnotationIndex(InputDocument.type);
	    Iterator inputDocumentIter = inputDocumentIndex.iterator();
	    if (inputDocumentIter.hasNext()) {
	        InputDocument inputDocument = (InputDocument) inputDocumentIter.next();
	        try {
				writeAnswers(outFile, inputDocument);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	  }
	  
	  private void writeAnswers(File fout, InputDocument inputDocument) throws IOException {
	  	FileOutputStream fos = new FileOutputStream(fout);
	  	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	   
	  	FSList questions = inputDocument.getQuestions();
	    while (questions instanceof NonEmptyFSList) {
		  FeatureStructure qHead = ((NonEmptyFSList)questions).getHead();
		  //do something with this element
		  Question question = (Question) qHead;
		  FSList passages = question.getPassages();
		    while (passages instanceof NonEmptyFSList) {
			  FeatureStructure aHead = ((NonEmptyFSList)passages).getHead();
			  //do something with this element
			  Passage passage = (Passage) aHead;	  
			  bw.write(String.format("%s %s %g %s\n", question.getId(), passage.getSourceDocId(), passage.getScore(), passage.getOriginalText()));
			  passages = ((NonEmptyFSList)passages).getTail();
			}
		  questions = ((NonEmptyFSList)questions).getTail();
		}
	  	
	   
	  	bw.close();
	  }

}
