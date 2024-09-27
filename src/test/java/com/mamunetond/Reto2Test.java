package com.mamunetond;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Reto2Test {

    WebDriver driver;

    @BeforeEach
    public void configuracionInicial() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testOrdenExitosa() {
        // ARRANGE
        driver.get("https://www.saucedemo.com/");
        // LOGIN ACT
        WebElement inputUser = driver.findElement(By.id("user-name"));
        WebElement inputPassword = driver.findElement(By.id("password"));
        WebElement buttonLogin = driver.findElement(By.id("login-button"));
        inputUser.sendKeys("standard_user");
        inputPassword.sendKeys("secret_sauce");
        buttonLogin.click();

        // INVENTORY PAGE
        WebElement buttonAddtocart_saucelabsback = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        WebElement buttonAddtocart_saucelabsbikelight = driver.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        buttonAddtocart_saucelabsback.click();
        buttonAddtocart_saucelabsbikelight.click();

        // CART PAGE
        WebElement buttonCart = driver.findElement(By.id("shopping_cart_container"));
        buttonCart.click();

        // CART ACT
        WebElement buttonCheckout = driver.findElement(By.id("checkout"));
        buttonCheckout.click();

        // CHECKOUT YOUR INFORMATION PAGE
        WebElement inputFirstName = driver.findElement(By.id("first-name"));
        WebElement inputLastName = driver.findElement(By.id("last-name"));
        WebElement inputPostalCode = driver.findElement(By.id("postal-code"));
        WebElement buttonContinue = driver.findElement(By.id("continue"));
        inputFirstName.sendKeys("Mario Alejandro");
        inputLastName.sendKeys("Muñetón Durango");
        inputPostalCode.sendKeys("055420");
        buttonContinue.click();

        // CHECKOUT OVERVIEW PAGE
        WebElement valueItem1 = driver.findElement(By.xpath("//div[contains(@data-test, 'inventory-item-price') and contains(string(), \"$29.99\")]"));
        WebElement valueItem2 = driver.findElement(By.xpath("//div[contains(@data-test, 'inventory-item-price') and contains(string(), \"$9.99\")]"));
        WebElement buttonFinish = driver.findElement(By.id("finish"));

        // VALIDATION
        try {
            valueItem1 = driver.findElement(By.xpath("//div[contains(@data-test, 'inventory-item-price') and contains(string(), \"$29.99\")]"));
            valueItem2 = driver.findElement(By.xpath("//div[contains(@data-test, 'inventory-item-price') and contains(string(), \"$9.99\")]"));
            Boolean validationValueItem1 = valueItem1.getText().equals("$29.99");
            Boolean validationValueItem2 = valueItem2.getText().equals("$9.99");

            if (validationValueItem1 && validationValueItem2) {
                buttonFinish.click();
            }
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            // Reintentar encontrar los elementos si hubo un cambio en el DOM
            valueItem1 = driver.findElement(By.xpath("//div[contains(@data-test, 'inventory-item-price') and contains(text(), '$29.99')]"));
            valueItem2 = driver.findElement(By.xpath("//div[contains(@data-test, 'inventory-item-price') and contains(text(), '$9.99')]"));
            Boolean validationValueItem1 = valueItem1.getText().equals("$29.99");
            Boolean validationValueItem2 = valueItem2.getText().equals("$9.99");

            if (validationValueItem1 && validationValueItem2) {
                buttonFinish.click();
            }
        }

        // ASSERT
        WebElement messageCheckoutcomplete = driver.findElement(By.xpath("//h2[@class='complete-header']"));
        assertEquals("Thank you for your order!", messageCheckoutcomplete.getText());
    }

    @AfterEach
    public void configuracionFinal() {
        driver.quit();
    }
}
