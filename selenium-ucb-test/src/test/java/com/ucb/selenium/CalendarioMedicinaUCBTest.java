package com.ucb.selenium;
import java.util.concurrent.TimeUnit;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.testng.Assert.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/****************************************/
//Historia de Usuario: Como estudiante de Medicina quiero verificar el calendario academico de Medicina
//
//Prueba de Aceptacion / Caso de Prueba TC:126:
//Verificar que la pagina de estudiantes de la UCB me muestre el calendario academico de Medicina
//
//PASO 1. Ingresar a la pagina de estudiantes de la UCB
//https://lpz.ucb.edu.bo/estudiantes/
//
//PASO 2. Hacer click en el boton Calendario Academico
//
//PASO 3. Hacer click en Calendario de Medicina
//
//Resultado Esperado:
//El calendario academico de Medicina debe estar visible en pantalla
/****************************************/

//Para ejecutar en la linea de comando:
//mvn clean compile test -Dtest=CalendarioMedicinaUCBTest

public class CalendarioMedicinaUCBTest {

    private WebDriver driver;

    @BeforeTest
    public void setDriver() throws Exception {

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

    }

    @Test
    public void verificarCalendarioMedicinaTest() {

        /********** Preparacion de la prueba **********/

        //PASO 1. Ingresar a la pagina de estudiantes de la UCB
        String url = "https://lpz.ucb.edu.bo/estudiantes/";
        driver.get(url);

        //Esperamos 5 segundos para que cargue la pagina
        try {
            TimeUnit.SECONDS.sleep(5);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Esperando a la pagina.....");

        /*********** Logica de la prueba ***********/

        //PASO 2. Hacer click en el boton Calendario Academico

        WebElement calendarioAcademico = driver.findElement(
                By.xpath("//*[contains(text(),'Calendario Académico')]")
        );

        System.out.println("Se muestra el texto del boton: "
                + calendarioAcademico.getText());

        calendarioAcademico.click();

        //Esperamos 3 segundos
        try {
            TimeUnit.SECONDS.sleep(3);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        //PASO 3. Hacer click en Calendario de Medicina

        WebElement calendarioMedicina = driver.findElement(
                By.xpath("//a[contains(text(),'Calendario de Medicina')]")
        );

        System.out.println("Se muestra el texto: "
                + calendarioMedicina.getText());

        calendarioMedicina.click();

        //Esperamos 5 segundos
        try {
            TimeUnit.SECONDS.sleep(5);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        /************ Verificacion de la situacion esperada - Assert ***************/

        WebElement tituloMedicina = driver.findElement(
                By.xpath("//*[contains(text(),'Medicina') or contains(text(),'MEDICINA')]")
        );

        boolean estaPresente = tituloMedicina.getText()
                .toUpperCase()
                .contains("MEDICINA");

        Assert.assertEquals(true, estaPresente);

        System.out.println("El calendario de Medicina se muestra correctamente.");

    }

    @AfterTest
    public void closeDriver() throws Exception {

        driver.quit();

    }

}