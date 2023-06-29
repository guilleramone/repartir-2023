package ar.com.grupoesfera.repartir.steps.grupos;

import ar.com.grupoesfera.repartir.steps.CucumberSteps;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ConNombreMayorADosCaracteresStep extends CucumberSteps {

    @Cuando("el usuario crea un grupo con el nombre {nombre} de mas de 2 caracteres")
    public void elUsuarioCreaUnGrupoIndicandoQueSuNombreEs(String nombre) {

        var crearGruposButton = driver.findElement(By.id("crearGruposButton"));
        crearGruposButton.click();

        driver.findElement(By.id("nombreGrupoNuevoInput")).sendKeys(nombre);

        var miembrosInput = driver.findElement(By.id("miembrosGrupoNuevoInput"));
        miembrosInput.sendKeys("Oscar");
        miembrosInput.sendKeys(Keys.ENTER);
        miembrosInput.sendKeys("Pablo");
        miembrosInput.sendKeys(Keys.ENTER);

        driver.findElement(By.id("guardarGrupoNuevoButton")).click();

        var wait = new WebDriverWait(driver, 2);
        wait.until(visibilityOfElementLocated(By.id("mensajesToast")));
    }

    @Entonces("visualiza el grupo con el nombre {nombre} de mas de 2 caracteres")
    public void visualizaElGrupoConElNombreIndicado(String nombre) {

        var grupoTR = driver.findElements(By.cssSelector("app-grupos table tr"));
        assertThat(grupoTR).hasSizeGreaterThan(1);

        var campoTDs = grupoTR.get(1).findElements(By.tagName("td"));
        assertThat(campoTDs.get(0).getText()).equals(nombre);
    }

    @Cuando("el usuario intenta crear un grupo con nombre {nombre} menor a 2 caracteres")
    public void elUsuarioIntentaCrearGrupoElNombreMenorADosCaracteres(String nombre) {

        var crearGruposButton = driver.findElement(By.id("crearGruposButton"));
        crearGruposButton.click();

        driver.findElement(By.id("nombreGrupoNuevoInput")).sendKeys(nombre);

        var miembrosInput = driver.findElement(By.id("miembrosGrupoNuevoInput"));
        miembrosInput.sendKeys("Oscar");

        miembrosInput.sendKeys(Keys.ENTER);
        miembrosInput.sendKeys("Pablo");
        miembrosInput.sendKeys(Keys.ENTER);

        driver.findElement(By.id("guardarGrupoNuevoButton")).click();

        var wait = new WebDriverWait(driver, 2);
        wait.until(visibilityOfElementLocated(By.id("mensajesToast")));
    }

    @Entonces("no debería crear el grupo con nombre {nombre} menor a 2 caracteres")
    public void noDeberiaCrearElGrupoConNombreIndicado() {

        var wait = new WebDriverWait(driver, 2);
        var mensajesToast = wait.withMessage("Mostro Toast")
                .until(visibilityOfElementLocated(By.id("mensajesToast")));
        wait.withMessage("Título del Toast es 'Error'")
                .until(textToBePresentInElement(mensajesToast, "Error"));
        assertThat(mensajesToast.getText())
                .as("Descripción del Toast")
                .contains("No se puede guardar");
    }
}