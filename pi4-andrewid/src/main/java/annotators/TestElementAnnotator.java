package annotators;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.util.FSCollectionFactory;
import org.apache.uima.jcas.cas.FSList;
import org.jsoup.Jsoup;

import type.*;

public class TestElementAnnotator extends JCasAnnotator_ImplBase {
  // create regular expression pattern for Question
	private Pattern questionPattern = Pattern.compile("(\\d+) (QUESTION)  (.*)");

	
	private boolean stringToBoolean(String str){
		if (str.equals("-1"))
			return false;
		else
			return true;
	}
	
	private String htmlToText(String html) {
	    return Jsoup.parse(html).text();
	}

  public void process(JCas aJCas) {
    
    // The JCas object is the data object inside UIMA where all the 
    // information is stored. It contains all annotations created by 
    // previous annotators, and the document text to be analyzed.
    InputDocument inputDocument = new InputDocument(aJCas);
    // get document text from JCas
    String docText = aJCas.getDocumentText();
    
    // search for Questions
    Matcher matcher = questionPattern.matcher(docText);
    int pos = 0;
    List<Question> questionList=new LinkedList<Question>();
    while (matcher.find(pos)){
    	Question question = new Question(aJCas);
    	question.setBegin(matcher.start(3));
    	question.setEnd(matcher.end());
    	question.setId(matcher.group(1));
    	question.setSentence(matcher.group(3));
    	questionList.add(question);
    	pos = matcher.end();
    }
    FSList questions = FSCollectionFactory.createFSList(aJCas, questionList);
    inputDocument.setQuestions(questions);
    
    
    List<Passage> passageList=new LinkedList<Passage>();
    for (Question question : questionList){
    	// search for Passages
    	List<Passage> questionPassageList=new LinkedList<Passage>();
    	// create regular expression pattern for Passage
    	Pattern passagePattern = Pattern.compile("("+question.getId()+") (\\w+\\.\\d+) (-1|1|2) (.*)");
        matcher = passagePattern.matcher(docText);
        pos = 0;
        while (matcher.find(pos)) {
          // match found - create the match as annotation in 
          // the JCas with some additional meta information
          Passage passage = new Passage(aJCas);
          passage.setBegin(matcher.start(4));
          passage.setEnd(matcher.end());
          passage.setQuestion(question);
          passage.setSourceDocId(matcher.group(2));
          passage.setLabel(stringToBoolean(matcher.group(3)));
          passage.setOriginalText(matcher.group(4));
          passage.setText(htmlToText(matcher.group(4)));
          questionPassageList.add(passage);
          pos = matcher.end();
        }
        FSList passages = FSCollectionFactory.createFSList(aJCas, questionPassageList);
        question.setPassages(passages);
        passageList.addAll(questionPassageList);
    }
    FSList passages = FSCollectionFactory.createFSList(aJCas, passageList);
    inputDocument.setPassages(passages);
    inputDocument.addToIndexes();
  }
}
