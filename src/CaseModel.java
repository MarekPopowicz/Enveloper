public class CaseModel implements Comparable<CaseModel>{
    private long Id;
    private String caseNo;
    private String SODNo;
    private String Addressee;


    public CaseModel(){

    }

    public CaseModel(String caseNo, String SODNo, String addressee) {
        this.caseNo = caseNo;
        this.SODNo = SODNo;
        Addressee = addressee;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getSODNo() {
        return SODNo;
    }

    public void setSODNo(String SODNo) {
        this.SODNo = SODNo;
    }

    public String getAddresse() {
        return Addressee;
    }

    public void setAddressee(String addressee) {
        Addressee = addressee;
    }


    @Override
    public int compareTo(CaseModel o) {
        return (int) (this.Id - o.getId());
    }
}
