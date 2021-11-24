package cz.czechitas.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DomaciUkolDI {

    public static final String WEB_URL = "https://cz-test-jedna.herokuapp.com/";
    WebDriver prohlizec;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        prohlizec = new FirefoxDriver();
        prohlizec.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    //  Task 1
    @Test
    public void rodicSExistujucimUctomMusiBytSchopnySaPrihlasit() {
        prohlizec.navigate().to(WEB_URL + "prihlaseni");
        meno = "Hermiona Granger";

        vyplnEmail("hermiona@granger.eu");
        vyplnHeslo("Harry1");

        klikniNaPrihlasit();

        overPrihlasenie();

    }

    //  Task 2
    @Test
    public void rodicVyberieKurzPrihlasiSaAPrihlasiNaKurzSvojeDieta() {
        prohlizec.navigate().to(WEB_URL + "31-trimesicni-kurzy-programova");

        vytvorPrihlasku();

        vyplnEmail("hermiona@granger.eu");
        vyplnHeslo("Harry1");
        klikniNaPrihlasit();
        vyberTermin();
        vyplnKrstneMeno("Hugo");
        vyplnPriezvisko("Granger-Weasley");
        vyplnDatumNarodenia("1.10.2014");
        platbaVHotovosti();
        prijatiePodmienok();
        vytvoreniePrihlasky();

        overeniePrihlasky();

    }


    //  Task 3
    @Test
    public void rodicSaPrihlasiDoAplikacieVyberieKurzAPrihlasiSvojeDieta() {
        prohlizec.navigate().to(WEB_URL + "prihlaseni");

        vyplnEmail("ronald@weasley.com");
        vyplnHeslo("Hogwarts1");
        klikniNaPrihlasit();

        novaPrihlaska();
        kurzWeb();
        vytvorPrihlasku();
        vyberTermin();
        vyplnKrstneMeno("Rose");
        vyplnPriezvisko("Granger-Weasley");
        vyplnDatumNarodenia("1.1.2016");
        platbaVHotovosti();
        prijatiePodmienok();
        vytvoreniePrihlasky();

        overeniePrihlasky();
    }

    //  Task 4
    @Test
    public void rodicSaPrihlasiDoAplikacieAOdhlasiDietaZKurzuZDovoduNemoci() {
        prohlizec.navigate().to(WEB_URL + "prihlaseni");

        vyplnEmail("hermiona@granger.eu");
        vyplnHeslo("Harry1");

        klikniNaPrihlasit();

        odhlasenieZiakaNemoc();

        overenieOdhlásenia();


    }

    private void overeniePrihlasky() {
        prohlizec.navigate().to(WEB_URL + "zaci");
        List<WebElement> zoznamPrihlasiek = prohlizec.findElements(By.xpath("//td[@class='dtr-control sorting_1']"));
        WebElement menoPrvehoZiaka = zoznamPrihlasiek.get(0);

        String menoZiaka = menoPrvehoZiaka.getText();
        Assertions.assertEquals("Rose Granger-Weasley", menoZiaka, "Prihlaska nie je taka ako cakas");
    }


    private void overenieOdhlásenia() {
        WebElement detail = prohlizec.findElement(By.xpath("//a[@class ='btn btn-sm btn-secondary' and (text()='Detail')]"));
        detail.click();

        WebElement odhlasenieNemoc = prohlizec.findElement(By.xpath("//ul/li[contains(.,'Z důvodu nemoci')]"));
        Assertions.assertNotNull(odhlasenieNemoc);
    }

    private void odhlasenieZiakaNemoc() {
        odhlasitUcast();

        WebElement nemoc = prohlizec.findElement(By.xpath("//label[@class ='custom-control-label' and (text()='Nemoc')]"));
        nemoc.click();

        WebElement odhlasitZaka = prohlizec.findElement(By.xpath("//input[@class='btn btn-primary']"));
        odhlasitZaka.click();
    }

    private void odhlasitUcast() {
        WebElement odhlasenieUcasti = prohlizec.findElement(By.xpath("//a[@class ='btn btn-sm btn-danger']"));
        odhlasenieUcasti.click();
    }


    public void kurzWeb() {
        WebElement kurzWeb = prohlizec.findElement(By.xpath("(//a[@class='btn btn-sm align-self-center btn-primary'])[2]"));
        kurzWeb.click();
    }


    public void novaPrihlaska() {
        WebElement novaPrihlaska = prohlizec.findElement(By.xpath("//a[@class='btn btn-sm btn-info']"));
        novaPrihlaska.click();
    }

    private void vyplnDatumNarodenia(String datumNarodenia) {
        vyplnInputBox("birthday", datumNarodenia);
    }

    private void vytvoreniePrihlasky() {
        WebElement tlacidloVytvoritPrihlasku = prohlizec.findElement(By.xpath("//input[@class='btn btn-primary mt-3']"));
        tlacidloVytvoritPrihlasku.click();
    }

    private void prijatiePodmienok() {
        WebElement podmienky = prohlizec.findElement(By.xpath("//label[@class ='custom-control-label' and contains(text(),'Souhlasím')]"));
        podmienky.click();
    }

    private void platbaVHotovosti() {
        WebElement sposobPlatby = prohlizec.findElement(By.xpath("//label[@class ='custom-control-label' and text()='Hotově'] "));
        sposobPlatby.click();
    }

    private void vyplnPriezvisko(String priezvisko) {
        vyplnInputBox("surname", priezvisko);
    }

    private void vyplnKrstneMeno(String krstneMeno) {
        vyplnInputBox("forename", krstneMeno);
    }

    private void vyberTermin() {
        WebElement termin = prohlizec.findElement(By.className("filter-option-inner-inner"));
        termin.click();
        WebElement terminKurzu = prohlizec.findElement(By.id("bs-select-1-0"));
        terminKurzu.click();
    }

    private void vytvorPrihlasku() {
        WebElement vytvoritPrihlasku = prohlizec.findElement(By.xpath("//a[@class='btn btn-sm align-self-center btn-primary']"));
        vytvoritPrihlasku.click();
    }

    private WebElement overPrihlasenie() {
        return prohlizec.findElement(By.xpath("//a[@title='" + meno + "']"));
    }

    String meno;

    private void klikniNaPrihlasit() {
        WebElement tlacitkoPrihlasit = prohlizec.findElement(By.xpath("//button[@class='btn btn-primary']"));
        tlacitkoPrihlasit.click();
    }

    private void vyplnHeslo(String heslo) {
        vyplnInputBox("password", heslo);
    }

    private void vyplnEmail(String email) {
        vyplnInputBox("email", email);
    }

    private void vyplnInputBox(String xPath, String hodnota) {
        WebElement inputElement = prohlizec.findElement(By.id(xPath));
        inputElement.sendKeys(hodnota);
    }


    @AfterEach
    public void tearDown() {
        prohlizec.quit();
    }
}
