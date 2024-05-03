import java.io.Serializable;

public class Output implements Serializable {

    // Attributes

    private List<String> compareResults;

    // Methods 

    public void compareOutput(){

    }

    public void generateCompareReport(){

    }

    // Set-get methods
    
    public void setCompareResults(List<String> compareResults) {
        this.compareResults = compareResults;
    }
    public List<String> getCompareResults() {
        return compareResults;
    }
}
