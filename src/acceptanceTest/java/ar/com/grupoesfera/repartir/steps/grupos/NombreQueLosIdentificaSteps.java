package ar.com.grupoesfera.repartir.steps.grupos;

import ar.com.grupoesfera.repartir.steps.CucumberSteps;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class NombreQueLosIdentificaSteps extends CucumberSteps {

    private String nombreIndicado;

    @Cuando("el usuario crea un grupo indicando el nombre {string}")
    public void elUsuarioCreaUnGrupoIndicandoElNombre(String nombre) {

        nombreIndicado = nombre;

        var wait = new WebDriverWait(driver, 2);
        var crearGruposButton = wait.until(elementToBeClickable(By.id("crearGruposButton")));
        crearGruposButton.click();

        driver.findElement(By.id("nombreGrupoNuevoInput")).sendKeys(nombreIndicado);

        var miembrosInput = driver.findElement(By.id("miembrosGrupoNuevoInput"));
        miembrosInput.sendKeys("Victor");
        miembrosInput.sendKeys(Keys.ENTER);
        miembrosInput.sendKeys("Brenda");
        miembrosInput.sendKeys(Keys.ENTER);

        driver.findElement(By.id("guardarGrupoNuevoButton")).click();

        wait.until(visibilityOfElementLocated(By.id("mensajesToast")));
    }

    @Entonces("debería visualizar dentro del listado el grupo con el nombre indicado")
    public void deberiaVisualizarDentroDelListadoElGrupoConElNombreIndicado() {

        var grupoTR = driver.findElements(By.cssSelector("app-grupos table tr"));
        assertThat(grupoTR).hasSizeGreaterThan(1);

        var campoTDs = grupoTR.get(1).findElements(By.tagName("td"));
        assertThat(campoTDs.get(0).getText()).isNotEmpty();
        assertThat(campoTDs.get(1).getText()).isEqualTo(nombreIndicado);
    }

    @Cuando("el usuario intenta crear un grupo sin indicar su nombre")
    public void elUsuarioIntentaCrearUnGrupoSinIndicarSuNombre() {

        var crearGruposButton = driver.findElement(By.id("crearGruposButton"));
        crearGruposButton.click();

        var miembrosInput = driver.findElement(By.id("miembrosGrupoNuevoInput"));
        miembrosInput.sendKeys("Carla");
        miembrosInput.sendKeys(Keys.ENTER);
        miembrosInput.sendKeys("Miguel");
        miembrosInput.sendKeys(Keys.ENTER);

        driver.findElement(By.id("guardarGrupoNuevoButton")).click();
    }

    @Entonces("no debería crear el grupo sin nombre")
    public void noDeberiaCrearElGrupoSinNombre() {

        // TODO
    }

    @Y("debería ser informado que no puede crear un grupo sin nombre")
    public void deberiaSerInformadoQueNoPuedeCrearUnGrupoSinNombre() {

        var wait = new WebDriverWait(driver, 2);
        var mensajesToast = wait.withMessage("Mostro Toast")
                .until(visibilityOfElementLocated(By.id("mensajesToast")));
        wait.withMessage("Título del Toast es 'Error'")
                .until(textToBePresentInElement(mensajesToast, "Error"));
        assertThat(mensajesToast.getText())
                .as("Descripción del Toast")
                .contains("No se puede guardar");
    }

    @Cuando("el usuario crea un grupo con el nombre Grupo De Prueba")
    public void ElUsuarioCreaUnGrupoConElNombreGrupoDePrueba() {
        var wait = new WebDriverWait(driver, 2);
        var crearGruposButton = wait.until(elementToBeClickable(By.id("crearGruposButton")));
        crearGruposButton.click();

        driver.findElement(By.id("nombreGrupoNuevoInput")).sendKeys("Grupo de prueba");

        var miembrosInput = driver.findElement(By.id("miembrosGrupoNuevoInput"));
        miembrosInput.sendKeys("Oscar");
        miembrosInput.sendKeys(Keys.ENTER);
        miembrosInput.sendKeys("Pablo");
        miembrosInput.sendKeys(Keys.ENTER);

        driver.findElement(By.id("guardarGrupoNuevoButton")).click();

        wait.until(visibilityOfElementLocated(By.id("mensajesToast")));
    }

    @Entonces("visualiza el grupo con el nombre Grupo De Prueba")
    public void visualizaElGrupoConElNombreGrupoDePrueba() {

        var grupoTR = driver.findElements(By.cssSelector("app-grupos table tr"));
        assertThat(grupoTR).hasSizeGreaterThanOrEqualTo(1);

        var campoTDs = grupoTR.get(1).findElements(By.tagName("td"));
        assertThat(campoTDs.get(0).getText()).isNotEmpty();
        assertThat(campoTDs.get(1).getText()).isEqualTo("Grupo de prueba");
    }

    @Cuando("el usuario intenta crear un grupo con nombre G")
    public void elUsuarioIntentaCrearUnGrupoConNombreG() {
        var wait = new WebDriverWait(driver, 2);
        var crearGruposButton = wait.until(elementToBeClickable(By.id("crearGruposButton")));
        crearGruposButton.click();

        driver.findElement(By.id("nombreGrupoNuevoInput")).sendKeys("G");

        var miembrosInput = driver.findElement(By.id("miembrosGrupoNuevoInput"));
        miembrosInput.sendKeys("Oscar");

        miembrosInput.sendKeys(Keys.ENTER);
        miembrosInput.sendKeys("Pablo");
        miembrosInput.sendKeys(Keys.ENTER);

        driver.findElement(By.id("guardarGrupoNuevoButton")).click();

        wait.until(visibilityOfElementLocated(By.id("mensajesToast")));
    }

    @Entonces("no debería crear el grupo con nombre G")
    public void noDeberiaCrearElGrupoConNombreG() {

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
