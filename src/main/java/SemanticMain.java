import org.apache.commons.lang3.time.StopWatch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SemanticMain {
    public List<String> listVocabulary = new ArrayList<>();  //List that contains all the vocabularies loaded from the csv file.
    public List<double[]> listVectors = new ArrayList<>(); //Associated vectors from the csv file.
    public List<Glove> listGlove = new ArrayList<>();
    public final List<String> STOPWORDS;

    public SemanticMain() throws IOException {
        STOPWORDS = Toolkit.loadStopWords();
        Toolkit.loadGLOVE();
    }

    public static void main(String[] args) throws IOException {
        StopWatch mySW = new StopWatch();
        mySW.start();
        SemanticMain mySM = new SemanticMain();
        mySM.listVocabulary = Toolkit.getListVocabulary();
        mySM.listVectors = Toolkit.getlistVectors();
        mySM.listGlove = mySM.CreateGloveList();

        List<CosSimilarityPair> listWN = mySM.WordsNearest("computer");
        Toolkit.PrintSemantic(listWN, 5);

        listWN = mySM.WordsNearest("phd");
        Toolkit.PrintSemantic(listWN, 5);

        List<CosSimilarityPair> listLA = mySM.LogicalAnalogies("china", "uk", "london", 5);
        Toolkit.PrintSemantic("china", "uk", "london", listLA);

        listLA = mySM.LogicalAnalogies("woman", "man", "king", 5);
        Toolkit.PrintSemantic("woman", "man", "king", listLA);

        listLA = mySM.LogicalAnalogies("banana", "apple", "red", 3);
        Toolkit.PrintSemantic("banana", "apple", "red", listLA);
        mySW.stop();

        if (mySW.getTime() > 2000)
            System.out.println("It takes too long to execute your code!\nIt should take less than 2 second to run.");
        else
            System.out.println("Well done!\nElapsed time in milliseconds: " + mySW.getTime());
    }

    public List<Glove> CreateGloveList() {
        List<Glove> listResult = new ArrayList<>();
        Toolkit.getListVocabulary();
        for(int i=0; i<listVocabulary.size();i++){
            boolean isStop=true;
            for(int j=0; j<STOPWORDS.size();j++){
                if(listVocabulary.get(i).equals(STOPWORDS.get(j))){
                    isStop=false;
                    break;
                }
            }
            if(isStop==true)
                listResult.add(new Glove(listVocabulary.get(i), new Vector(listVectors.get(i))));

        }
        return listResult;
    }
    public List<CosSimilarityPair> WordsNearest(String _word) {
        List<CosSimilarityPair> listCosineSimilarity = new ArrayList<>();
        Vector _vec = null;
        for(Glove glove : listGlove){
            if(glove.getVocabulary().equals(_word)){
                _vec= glove.getVector();
                break;
            }
        }
        if(_vec == null)
        {
            for(Glove glove: listGlove)
            {
                if(glove.getVocabulary().equals("error"))
                {
                    _word = "error";
                    _vec = glove.getVector();
                    break;
                }
            }
        }
        for(Glove glove: listGlove)
        {
            if(Objects.equals(glove.getVocabulary(), _word))
                continue;
            CosSimilarityPair csp =new CosSimilarityPair(_word, glove.getVocabulary(), _vec.cosineSimilarity(glove.getVector()));
            listCosineSimilarity.add(csp);
        }
        HeapSort.doHeapSort(listCosineSimilarity);

        return listCosineSimilarity;
    }

    public List<CosSimilarityPair> WordsNearest(Vector _vector) {
        List<CosSimilarityPair> listCosineSimilarity = new ArrayList<>();
        for(Glove glove : listGlove) {
            if (glove.getVector().equals(_vector))
                continue;
            else {


                    CosSimilarityPair csp = new CosSimilarityPair(_vector, glove.getVocabulary(), _vector.cosineSimilarity(glove.getVector()));
                    listCosineSimilarity.add(csp);

            }
        }
        HeapSort.doHeapSort(listCosineSimilarity);
        return listCosineSimilarity;
    }
    /**
     * Method to calculate the logical analogies by using references.
     * <p>
     * Example: uk is to london as china is to XXXX.
     *       _firISRef  _firTORef _secISRef
     * In the above example, "uk" is the first IS reference; "london" is the first TO reference
     * and "china" is the second IS reference. Moreover, "XXXX" is the vocabulary(ies) we'd like
     * to get from this method.
     * <p>
     * If _top <= 0, then returns an empty listResult.
     * If the vocabulary list does not include _secISRef or _firISRef or _firTORef, then returns an empty listResult.
     *
     * @param _secISRef The second IS reference
     * @param _firISRef The first IS reference
     * @param _firTORef The first TO reference
     * @param _top      How many vocabularies to include.
     */
    public List<CosSimilarityPair> LogicalAnalogies(String _secISRef, String _firISRef, String _firTORef, int _top) {
        List<CosSimilarityPair> listResult = new ArrayList<>();
        if(_top<= 0 || !listVocabulary.contains(_secISRef) || !listVocabulary.contains(_firISRef) || !listVocabulary.contains(_firTORef)){
            return listResult;
        }
        else
        {
            Vector a = new Vector(listVectors.get(listVocabulary.indexOf(_firTORef)));
            Vector b = new Vector(listVectors.get(listVocabulary.indexOf(_firISRef)));
            Vector c = new Vector(listVectors.get(listVocabulary.indexOf(_secISRef)));
            Vector vNew = c.subtraction(b).add(a);
            List<CosSimilarityPair> listOrder = WordsNearest(vNew);
            for(int i=0; i<listOrder.size(); i++)
            {
                if (!(listOrder.get(i).getWord2().equals(_firISRef) || listOrder.get(i).getWord2().equals(_firTORef) || listOrder.get(i).getWord2().equals(_secISRef)))
                    listResult.add(listOrder.get(i));
                if(listResult.size() == _top)
                {
                    break;
                }
            }
        }
        return listResult;
    }
}
