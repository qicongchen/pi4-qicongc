

/* First created by JCasGen Sat Sep 26 09:15:57 EDT 2015 */
package type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



import org.apache.uima.jcas.cas.FSList;


/** Stores the information of the passage.
 * Updated by JCasGen Sun Sep 27 16:11:18 EDT 2015
 * XML source: /Users/yuchenluo/git/pi4-qicongc/pi4-andrewid/src/main/resources/descriptors/typeSystem.xml
 * @generated */
public class Passage extends ComponentAnnotation implements Comparable<Passage>{
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Passage.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Passage() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Passage(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Passage(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Passage(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: text

  /** getter for text - gets The passage text extracted from the source document.
   * @generated
   * @return value of the feature 
   */
  public String getText() {
    if (Passage_Type.featOkTst && ((Passage_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "type.Passage");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Passage_Type)jcasType).casFeatCode_text);}
    
  /** setter for text - sets The passage text extracted from the source document. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setText(String v) {
    if (Passage_Type.featOkTst && ((Passage_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "type.Passage");
    jcasType.ll_cas.ll_setStringValue(addr, ((Passage_Type)jcasType).casFeatCode_text, v);}    
   
    
  //*--------------*
  //* Feature: label

  /** getter for label - gets This stores a gold standard label of this answer.  'True' means that it answers the question; otherwise, it is does not.
   * @generated
   * @return value of the feature 
   */
  public boolean getLabel() {
    if (Passage_Type.featOkTst && ((Passage_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "type.Passage");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Passage_Type)jcasType).casFeatCode_label);}
    
  /** setter for label - sets This stores a gold standard label of this answer.  'True' means that it answers the question; otherwise, it is does not. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLabel(boolean v) {
    if (Passage_Type.featOkTst && ((Passage_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "type.Passage");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Passage_Type)jcasType).casFeatCode_label, v);}    
   
    
  //*--------------*
  //* Feature: sourceDocId

  /** getter for sourceDocId - gets This specifies the source document id to which the passage belongs to.
   * @generated
   * @return value of the feature 
   */
  public String getSourceDocId() {
    if (Passage_Type.featOkTst && ((Passage_Type)jcasType).casFeat_sourceDocId == null)
      jcasType.jcas.throwFeatMissing("sourceDocId", "type.Passage");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Passage_Type)jcasType).casFeatCode_sourceDocId);}
    
  /** setter for sourceDocId - sets This specifies the source document id to which the passage belongs to. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSourceDocId(String v) {
    if (Passage_Type.featOkTst && ((Passage_Type)jcasType).casFeat_sourceDocId == null)
      jcasType.jcas.throwFeatMissing("sourceDocId", "type.Passage");
    jcasType.ll_cas.ll_setStringValue(addr, ((Passage_Type)jcasType).casFeatCode_sourceDocId, v);}    
   
    
  //*--------------*
  //* Feature: question

  /** getter for question - gets A question associated with this passage.
   * @generated
   * @return value of the feature 
   */
  public Question getQuestion() {
    if (Passage_Type.featOkTst && ((Passage_Type)jcasType).casFeat_question == null)
      jcasType.jcas.throwFeatMissing("question", "type.Passage");
    return (Question)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Passage_Type)jcasType).casFeatCode_question)));}
    
  /** setter for question - sets A question associated with this passage. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setQuestion(Question v) {
    if (Passage_Type.featOkTst && ((Passage_Type)jcasType).casFeat_question == null)
      jcasType.jcas.throwFeatMissing("question", "type.Passage");
    jcasType.ll_cas.ll_setRefValue(addr, ((Passage_Type)jcasType).casFeatCode_question, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: originalText

  /** getter for originalText - gets 
   * @generated
   * @return value of the feature 
   */
  public String getOriginalText() {
    if (Passage_Type.featOkTst && ((Passage_Type)jcasType).casFeat_originalText == null)
      jcasType.jcas.throwFeatMissing("originalText", "type.Passage");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Passage_Type)jcasType).casFeatCode_originalText);}
    
  /** setter for originalText - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setOriginalText(String v) {
    if (Passage_Type.featOkTst && ((Passage_Type)jcasType).casFeat_originalText == null)
      jcasType.jcas.throwFeatMissing("originalText", "type.Passage");
    jcasType.ll_cas.ll_setStringValue(addr, ((Passage_Type)jcasType).casFeatCode_originalText, v);}

	@Override
	public int compareTo(Passage o) {
		// TODO Auto-generated method stub
		if (this.getScore() > o.getScore())
			return -1;
		else if (this.getScore() < o.getScore())
			return 1;
		else
			return 0;
	}    
  }

    