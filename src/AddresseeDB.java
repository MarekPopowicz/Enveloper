public class AddresseeDB {

    //TABLE_ADDRESSEE
    public static final String TABLE_ADDRESSEE = "Addressee";
    public static final String COLUMN_ADDRESSEE_ID = "AddresseeId";
    public static final String COLUMN_ADDRESSEE_FIRSTNAME ="AddresseeFirstname";
    public static final String COLUMN_ADDRESSEE_LASTNAME ="AddresseeLastname";
    public static final String COLUMN_ADDRESSEE_ADDRESS ="AddresseeAddress";
    public static final String COLUMN_ADDRESSEE_ZIPCODE = "AddresseeZipCode";
    public static final String COLUMN_ADDRESSEE_POST = "AddresseePost";

    //TABLE_CASE
    public static final String TABLE_CASE = "Cases";
    public static final String COLUMN_CASE_ID = "CaseId";
    public static final String COLUMN_CASE_NUMBER ="CaseNumber";
    public static final String COLUMN_CASE_SOD_NUMBER ="CaseSODNumber";


    //SQL_CREATE_TABLE_ADDRESSEE
    public static final String SQL_CREATE_TABLE_ADDRESSEE = "CREATE TABLE IF NOT EXISTS " + TABLE_ADDRESSEE + "(" +
            COLUMN_ADDRESSEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_ADDRESSEE_FIRSTNAME +  " NVARCHAR(255)  NOT NULL, " +
            COLUMN_ADDRESSEE_LASTNAME +  " NVARCHAR(255), " +
            COLUMN_ADDRESSEE_ADDRESS +  " NVARCHAR(100)  NOT NULL, " +
            COLUMN_ADDRESSEE_ZIPCODE +  " NVARCHAR(6)  NOT NULL, " +
            COLUMN_ADDRESSEE_POST + " NVARCHAR(100)  NOT NULL);";


    //SQL_CREATE_TABLE_CASE
    public static final String SQL_CREATE_TABLE_CASE = "CREATE TABLE IF NOT EXISTS " + TABLE_CASE + "(" +
            COLUMN_CASE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_CASE_NUMBER +  " NVARCHAR(100)  NOT NULL, " +
            COLUMN_CASE_SOD_NUMBER +  " NVARCHAR(100)  NOT NULL, " +
            COLUMN_ADDRESSEE_ID +  " INTEGER  NOT NULL, " +
            "FOREIGN KEY (" + COLUMN_ADDRESSEE_ID + ") REFERENCES \"" + TABLE_ADDRESSEE + "\"(" + COLUMN_ADDRESSEE_ID + ") " +
            "ON DELETE NO ACTION ON UPDATE NO ACTION );";

    //SQL_INSERT_PARCEL
    public static final String SQL_INSERT_ADDRESSEE = "INSERT INTO " + TABLE_ADDRESSEE + " (" +
            COLUMN_ADDRESSEE_FIRSTNAME + ", " +
            COLUMN_ADDRESSEE_LASTNAME + ", " +
            COLUMN_ADDRESSEE_ADDRESS + ", " +
            COLUMN_ADDRESSEE_ZIPCODE + ", " +
            COLUMN_ADDRESSEE_POST + ") VALUES " +
            "('Urz??d Miasta Wroc??awia', 'Wydzia?? Nieruchomo??ci Komunalnych', 'al. Marcina Kromera 44', '51-163', 'Wroc??aw'), " +
            "('Urz??d Miasta Wroc??awia', 'Wydzia?? Nieruchomo??ci Skarbu Pa??stwa', 'ul. G. Zapolskiej 4', '50-032', 'Wroc??aw'), " +
            "('Zarz??d Zieleni Miejskiej', 'Dzia?? Uzgodnie??', 'ul. Trzebnicka 33', '50-231', 'Wroc??aw'), " +
            "('Zarz??d Zasobu Komunalnego', 'Dzia?? Techniczny', 'ul. Spi??owa 29/31', '53-442', 'Wroc??aw'), " +
            "('Wroc??awskie Mieszkania Sp. z o.o.', 'Zesp???? Techniczno-Inwestycyjny', 'ul. Namys??owska 8', '50-304', 'Wroc??aw'), " +
            "('Krajowy O??rodek Wsparcia Rolnictwa', 'Wydzia?? Gospodarowania Zasobem', 'ul. Mi??ska 60', '54-610', 'Wroc??aw'); ";

}
