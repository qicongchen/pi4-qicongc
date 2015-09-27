package annotators;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.fit.util.FSCollectionFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.NonEmptyFSList;
import org.apache.uima.resource.ResourceInitializationException;

import type.*;

public class TokenAnnotator extends JCasAnnotator_ImplBase {
	private Pattern tokenPattern = Pattern.compile("\\b\\w+\\b");
    private int K;
   
  private double I(int len){
	  if (len >= K){
		  return (K+0.0)/len;
	  }else{
		  return 1;
	  }
  }
  
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
    // Get config. parameter values
    K = (Integer)aContext.getConfigParameterValue("K");
  }

  public void process(JCas aJCas) {
    
    // The JCas object is the data object inside UIMA where all the 
    // information is stored. It contains all annotations created by 
    // previous annotators, and the document text to be analyzed.
	  
    FSIndex inputDocumentIndex = aJCas.getAnnotationIndex(InputDocument.type);
    Iterator inputDocumentIter = inputDocumentIndex.iterator();
    if (inputDocumentIter.hasNext()) {
        InputDocument inputDocument = (InputDocument) inputDocumentIter.next();
        // load dictionary
        Map<String, Token> tokenMap = new HashMap<String, Token>();
        FSList dictionary = inputDocument.getDictionary(); 
        while (dictionary instanceof NonEmptyFSList) {
      	  FeatureStructure head = ((NonEmptyFSList)dictionary).getHead();
      	  //do something with this element
      	  Token token = (Token) head;
      	  tokenMap.put(token.getText(), token);
      	  dictionary = ((NonEmptyFSList)dictionary).getTail();
      	}
        
        
        FSList questions = inputDocument.getQuestions();
        while (questions instanceof NonEmptyFSList) {
    	  FeatureStructure qHead = ((NonEmptyFSList)questions).getHead();
    	  //do something with this element
    	  Question question = (Question) qHead;
    	  Matcher matcher = tokenPattern.matcher(question.getSentence());
      	  int pos = 0;
      	  Set<String> qTokenTextSet = new HashSet<String>();
          while (matcher.find(pos)) {
            // match found - create the match as annotation in 
            // the JCas with some additional meta information
            String tokenText = matcher.group();
            if (!qTokenTextSet.contains(tokenText)){
            	qTokenTextSet.add(tokenText);
            }
            pos = matcher.end();
          }
          FSList passages = question.getPassages();
          List<Passage> passageList = new ArrayList<Passage>();
          while (passages instanceof NonEmptyFSList) {
	      	  FeatureStructure aHead = ((NonEmptyFSList)passages).getHead();
	      	  //do something with this element
	      	  Passage passage = (Passage) aHead;
	      	  
	      	  matcher = tokenPattern.matcher(passage.getText());
	      	  pos = 0;
	      	  Set<String> aTokenTextSet = new HashSet<String>();
	          while (matcher.find(pos)) {
	            // match found - create the match as annotation in 
	            // the JCas with some additional meta information
	            String tokenText = matcher.group();
	            if (!aTokenTextSet.contains(tokenText)){
	            	aTokenTextSet.add(tokenText);
	            }
	            pos = matcher.end();
	          }
	          
	          int overlap = 0;
	      	  double score = 0.0;
	      	  for (String qTokenText : qTokenTextSet){
	      		  if (aTokenTextSet.contains(qTokenText)){
	      			  Token token = tokenMap.get(qTokenText);
	      			  
	      			  score += token.getScore();
	      			  overlap++;
	      		  }
	      	  }
	      	  score += overlap*Math.log(I(passage.getText().length()));
	      	  passage.setScore(score);
	      	  passageList.add(passage);
	      	  passages = ((NonEmptyFSList)passages).getTail();
	      	}
          Collections.sort(passageList);
          passages = FSCollectionFactory.createFSList(aJCas, passageList);
          question.setPassages(passages);
    	  questions = ((NonEmptyFSList)questions).getTail();
    	}
       
    }
   
  }
}
