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
            "('Urząd Miasta Wrocławia', 'Wydział Nieruchomości Komunalnych', 'al. Marcina Kromera 44', '51-163', 'Wrocław'), " +
            "('Urząd Miasta Wrocławia', 'Wydział Nieruchomości Skarbu Państwa', 'ul. G. Zapolskiej 4', '50-032', 'Wrocław'), " +
            "('Zarząd Zieleni Miejskiej', 'Dział Uzgodnień', 'ul. Trzebnicka 33', '50-231', 'Wrocław'), " +
            "('Zarząd Zasobu Komunalnego', 'Dział Techniczny', 'ul. Spiżowa 29/31', '53-442', 'Wrocław'), " +
            "('Wrocławskie Mieszkania Sp. z o.o.', 'Zespół Techniczno-Inwestycyjny', 'ul. Namysłowska 8', '50-304', 'Wrocław'), " +
            "('Krajowy Ośrodek Wsparcia Rolnictwa', 'Wydział Gospodarowania Zasobem', 'ul. Mińska 60', '54-610', 'Wrocław'); ";

}
