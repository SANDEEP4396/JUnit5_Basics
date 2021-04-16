import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//If we use this then there is no need to mark @BeforeALl and @AfterALl as static
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContactManagerTest {

    private static ContactManager contactManager;
    @BeforeEach
    public  void setup(){
         contactManager = new ContactManager();
         System.out.println("Creating Contact manager instance before test execution");
    }
    @BeforeAll
    public  void setupAll(){
        System.out.println("Should print before all");
    }
    @Test
    @DisplayName("Should Create Contact")
  //  @EnabledOnOs(value = OS.MAC,disabledReason = "Should run only on MAC")
    @RepeatedTest(3)
    public void shouldCreateContact(){
        contactManager.addContact("Sandeep Sagar","Muralidhar","0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }
    @Test
    @DisplayName("Should not create contact when first name is Null")
    public void shouldThrowRunTimeExceptionWhenFirstNameIsNull(){
        ContactManager contactManager = new ContactManager();
        Assertions.assertThrows(RuntimeException.class,()->
        {contactManager.addContact(null,"Muralidhar","0123456789");});
    }
    @Test
    @DisplayName("Should not create contact when last name is Null")
    public void shouldThrowRunTimeExceptionWhenLastNameIsNull(){
        ContactManager contactManager = new ContactManager();
        Assertions.assertThrows(RuntimeException.class,()->
        {contactManager.addContact("Sandeep Sagar",null,"0123456789");});
    }
    @Test
    @DisplayName("Should not create contact when last name is Null")
    public void shouldThrowRunTimeExceptionWhenPhoneNumberIsNull(){
        ContactManager contactManager = new ContactManager();
        Assertions.assertThrows(RuntimeException.class,()->
        { contactManager.addContact("Sandeep Sagar","Muralidhar",null);});
    }
    @Test
    @DisplayName("Test on dev machine")
    public void shouldTestCreationOnlyOnDEV(){
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV"))); //Executes only on DEV machine
        contactManager.addContact("Sandeep Sagar","Muralidhar","0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }
    @Test
    @DisplayName("Should Validate Phone number format - CSV Format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldValidatePhoneNumberFormat(){
        contactManager.addContact("Sandeep Sagar","Muralidhar","0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }
    @Nested
    class ParameterizedTests {
        @DisplayName("Phone Number should match the required Format")
        @ParameterizedTest
        @ValueSource(strings = {"0123456789", "0123456798", "0123456897"})
        public void shouldTestPhoneNumberFormatUsingValueSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }

        @DisplayName("CSV Source Case - Phone Number should match the required Format")
        @ParameterizedTest
        @CsvSource({"0123456789", "0123456798", "0123456897"})
        public void shouldTestPhoneNumberFormatUsingCSVSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }

        @DisplayName("CSV File Source Case - Phone Number should match the required Format")
        @ParameterizedTest
        @CsvFileSource(resources = "/data.csv")
        public void shouldTestPhoneNumberFormatUsingCSVFileSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }
    }
    private List<String> phoneNumberList(){
        return Arrays.asList("0123456789","0123456789","0123456789");
    }
    @AfterEach
    public void tearDown(){
        System.out.println("Executes after each test");
    }
    @AfterAll
    public  void tearDownAll(){
        System.out.println("Executes after test");
    }
}
