package com.ucb.selenium;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.testng.Assert.*;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/****************************************/
//Historia de Usuario:
//Como estudiante de Medicina quiero verificar el calendario academico
//y descargar el archivo PDF del calendario.
//
//Prueba de Aceptacion / Caso de Prueba TC:126:
//Verificar que la pagina de estudiantes de la UCB
//muestre el calendario academico de Medicina
//y permita descargar el archivo PDF.
//
//PASO 1. Ingresar a la pagina de estudiantes de la UCB
//https://lpz.ucb.edu.bo/estudiantes/
//
//PASO 2. Hacer click en el boton Calendario Academico
//
//PASO 3. Hacer click en Calendario de Medicina
//
//PASO 4. Verificar que el archivo PDF se descargue correctamente
//
//Resultado Esperado:
//El calendario academico de Medicina debe mostrarse
//y el archivo PDF debe descargarse correctamente.
/****************************************/

//Para ejecutar en linea de comando:
//mvn clean compile test -Dtest=CalendarioMedicinaUCBTest

public class CalendarioMedicinaUCBTest {

    private WebDriver driver;

    @BeforeTest
    public void setDriver() throws Exception {

        /******** Configuracion carpeta de descargas ********/

        String downloadPath = System.getProperty("user.dir") + "/downloads";

        File downloadDir = new File(downloadPath);

        if (!downloadDir.exists()) {
            downloadDir.mkdir();
        }

        /******** Configuracion Chrome para descargar PDFs ********/

        Map<String, Object> prefs = new HashMap<String, Object>();

        prefs.put("download.default_directory", downloadPath);

        //Hace que Chrome descargue el PDF en vez de abrirlo
        prefs.put("plugins.always_open_pdf_externally", true);

        ChromeOptions options = new ChromeOptions();

        options.setExperimentalOption("prefs", prefs);

        /******** Inicializacion Selenium ********/

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

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

        //Esperamos 8 segundos para la descarga

        try {
            TimeUnit.SECONDS.sleep(8);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        /************ Verificacion del PDF descargado ***************/

        String downloadPath = System.getProperty("user.dir") + "/downloads";

        File carpetaDescargas = new File(downloadPath);

        File[] archivos = carpetaDescargas.listFiles();

        boolean pdfDescargado = false;

        if (archivos != null) {

            for (File archivo : archivos) {

                System.out.println("Archivo encontrado: "
                        + archivo.getName());

                if (archivo.getName()
                        .toLowerCase()
                        .endsWith(".pdf")) {

                    pdfDescargado = true;

                    System.out.println("PDF descargado correctamente: "
                            + archivo.getName());

                    break;
                }
            }
        }

        /************ Assert ***************/

        Assert.assertEquals(true, pdfDescargado);

        System.out.println("Prueba exitosa: El PDF fue descargado.");

    }

    @AfterTest
    public void closeDriver() throws Exception {

        driver.quit();

    }

}