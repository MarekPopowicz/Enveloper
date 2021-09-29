public interface CaseTableListener {

    public void rowDeleted(int rowIndex);
    public void rowAdded(CaseModel addressee);
    public void rowUpdated(int rowIndex, CaseModel addressee);

}
