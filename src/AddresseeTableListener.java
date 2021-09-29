public interface AddresseeTableListener {
    public void rowDeleted(int rowIndex);
    public void rowAdded(AddresseeModel addressee);
    public void rowUpdated(int rowIndex, AddresseeModel addressee);
}
