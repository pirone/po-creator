package br.com.pirone.pocreator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PageObjectCreator {

	private ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File("C:\\chromedriver.exe"))
			.usingAnyFreePort().build();
	private WebDriver driver;
	
	@AfterTest
	public void afterTest() {
		driver.quit();
		service.stop();
	}
	
	@BeforeTest
	public void before() throws IOException {
		service.start();
		driver = new ChromeDriver(service);
	}
	
	@Test
	public void getElementos() throws IOException {
//		public static Input input = new Input(id);
		
		
		driver.get("https://www.kabum.com.br/");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		List<?> listaInputs = filtraItensVazios((ArrayList<?>)js.executeScript(comandoJS(TipoElemento.INPUT)));
		List<?> listaButtons = filtraItensVazios((ArrayList<?>)js.executeScript(comandoJS(TipoElemento.BUTTON)));
		List<?> listaCombos = filtraItensVazios((ArrayList<?>)js.executeScript(comandoJS(TipoElemento.COMBO)));
		
		StringBuilder sb = new StringBuilder();
		listaInputs.stream().forEach(input -> sb.append(saidaElemento(input, TipoElemento.INPUT)).toString());
		listaButtons.stream().forEach(button -> sb.append(saidaElemento(button, TipoElemento.BUTTON)).toString());
		listaCombos.stream().forEach(combo -> sb.append(saidaElemento(combo, TipoElemento.COMBO)).toString());

		System.out.println("Numero de elementos localizados: " +(listaButtons.size()+listaCombos.size()+listaInputs.size()));
		System.out.println(sb.toString());

	}

	private String saidaElemento(Object id, TipoElemento tipoElemento){
		return "public static "+tipoElemento.getClasse()+" "+tipoElemento.getValor()+id+" = new "+tipoElemento.getClasse()+"(\""+id+"\");\n";
	}

	private String comandoJS(TipoElemento tipoElemento) {
		if (tipoElemento.equals(TipoElemento.BUTTON) || tipoElemento.equals(TipoElemento.INPUT)) {
			return "var IDs = [];" +
					"$(\"form\").find(\""+tipoElemento.getValorComParametros()+"\").each(function(){ IDs.push(this.id); });"+
					"return IDs;";
		}
		return "var IDs = [];" +
				"$(\"form\").find(\""+tipoElemento.getValor()+"\").each(function(){ IDs.push(this.id); });"+
				"return IDs;";
	}
	
	private List<Object> filtraItensVazios(ArrayList<?> arrayList) {
		return new ArrayList<>(arrayList.stream().filter(id -> id!="").collect(Collectors.toList()));
	}
	
}
