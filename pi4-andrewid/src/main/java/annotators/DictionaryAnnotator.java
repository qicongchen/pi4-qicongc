package annotators;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.fit.util.FSCollectionFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.NonEmptyFSList;

import type.*;

public class DictionaryAnnotator extends JCasAnnotator_ImplBase {
	private Pattern tokenPattern = Pattern.compile("\\b\\w+\\b");


  public void process(JCas aJCas) {
    
    // The JCas object is the data object inside UIMA where all the 
    // information is stored. It contains all annotations created by 
    // previous annotators, and the document text to be analyzed.
    FSIndex inputDocumentIndex = aJCas.getAnnotationIndex(InputDocument.type);
    Iterator inputDocumentIter = inputDocumentIndex.iterator();
    if (inputDocumentIter.hasNext()) {
        InputDocument inputDocument = (InputDocument) inputDocumentIter.next();
        
        Map<Token, Integer> tokenMap = new HashMap<Token, Integer>();
        int docNum = 0;
        
        FSList passages = inputDocument.getPassages(); 
        while (passages instanceof NonEmptyFSList) {
    	  FeatureStructure head = ((NonEmptyFSList)passages).getHead();
    	  //do something with this element
    	  Passage passage = (Passage) head;
    	  Matcher matcher = tokenPattern.matcher(passage.getText());
      	  int pos = 0;
      	  Set<Token> tokenSet = new HashSet<Token>();
          while (matcher.find(pos)) {
            // match found - create the match as annotation in 
            // the JCas with some additional meta information
        	Token token = new Token(aJCas);
        	token.setText(matcher.group());
        	if (!tokenSet.contains(token)){
        		tokenSet.add(token);
        	}
            pos = matcher.end();
          }
          for (Token token : tokenSet){
        	  if (!tokenMap.containsKey(token)){
        		  tokenMap.put(token, 1);
        	  }else{
        		  tokenMap.put(token, tokenMap.get(token)+1);
        	  }
          }
          docNum += 1;
    	  passages = ((NonEmptyFSList)passages).getTail();
    	}
        List<Token> tokenList = new ArrayList<Token>();
        for (Entry<Token,Integer> entry : tokenMap.entrySet()){
        	Token token = entry.getKey();
        	int value = entry.getValue();
        	double idf = Math.log((docNum+0.0)/value);
        	token.setScore(idf);
        	tokenList.add(token);
        }
        FSList tokens = FSCollectionFactory.createFSList(aJCas, tokenList);
        inputDocument.setDictionary(tokens);
    }
   
  }
}
