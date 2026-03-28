package praktikum.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.*;
import org.openqa.selenium.support.FindBy;

import java.io.File;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;
import static java.time.Duration.ofSeconds;

@Feature("Создание объявления")
public class AdPage {

    @FindBy(xpath = "//h1[@class='hi createListing_title__IFtFs']")
    private SelenideElement title;

    @FindBy(xpath = "(//input[@class='upload_hiddenInput__sCsY-'])[1]")
    private SelenideElement addFoto1;

    @FindBy(xpath = "//input[@placeholder='Название']")
    private SelenideElement inputName;

    @FindBy(xpath = "(//button[@class='dropDownMenu_arrowDown__pfGL1 dropDownMenu_noDefault__wSKsP'])[1]")
    private SelenideElement dropDownMenuCategory;

    @FindBy(xpath = "(//input[@name='condition']/following-sibling::div)[1]")
    private SelenideElement radioNew;

    @FindBy(xpath = "(//input[@name='condition']/following-sibling::div)[2]")
    private SelenideElement radioNoNew;

    @FindBy(xpath = "(//button[@class='dropDownMenu_arrowDown__pfGL1 dropDownMenu_noDefault__wSKsP'])[2]")
    private SelenideElement dropDownMenuCity;

    @FindBy(xpath = "//textarea[@placeholder='Описание товара']")
    private SelenideElement inputProductDescription;

    @FindBy(xpath = "//input[@placeholder='Стоимость']")
    private SelenideElement inputPrice;

    @FindBy(xpath = "//button[normalize-space(text())='Опубликовать']")
    private SelenideElement buttonPublish;

    private ElementsCollection dropdownCategoryOptions = $$("div[class*='dropDownMenu_options'] button[class*='dropDownMenu_btn']");
    private ElementsCollection dropdownCityOptions = $$("div[class*='dropDownMenu_options'] button[class*='dropDownMenu_btn']");

    public AdPage() {
        page(this);
    }

    @Step("Загрузка фотографии объявления")
    public void uploadFoto() {
        addFoto1.uploadFile(new File("src/test/resources/foto/kandinsky_image1.jpg"));
    }

    @Step("Проверка заголовка страницы")
    public void getTitle(String expectedText) {
        title.shouldBe(visible).shouldHave(text(expectedText));
    }

    @Step("Ввод названия объявления")
    public void setInputName(String name) {
        inputName.setValue(name);
    }

    @Step("Выбор категории")
    public void setDropDownMenuCategory(String category) {
        dropDownMenuCategory.click();
        dropdownCategoryOptions.shouldHave(sizeGreaterThan(0), ofSeconds(5));
        dropdownCategoryOptions.findBy(text(category)).click();
    }

    @Step("Установка статуса товара")
    public void setRadioNoNew() {
        radioNoNew.click();
    }

    @Step("Ввод описания товара")
    public void setInputProductDescription(String text) {
        inputProductDescription.setValue(text);
    }

    @Step("Ввод стоимости товара")
    public void setInputPrice(Integer price) {
        inputPrice.setValue(String.valueOf(price));
    }

    @Step("Выбор города")
    public void setDropDownMenuCity(String city) {
        dropDownMenuCity.click();
        dropdownCityOptions.shouldHave(sizeGreaterThan(0), ofSeconds(5));
        dropdownCityOptions.findBy(text(city)).click();
    }

    @Step("Нажатие кнопки 'Опубликовать'")
    public void clickButtonPublish() {
        buttonPublish.click();
    }

    @Step("Публикация объявления")
    @Description("Полный процесс создания объявления с загрузкой фото, заполнением всех полей и публикацией")
    public void publishAd(String name, String category, String city, String description, Integer price) {
        uploadFoto();
        setInputName(name);
        setDropDownMenuCategory(category);
        setRadioNoNew();
        setDropDownMenuCity(city);
        setInputProductDescription(description);
        setInputPrice(price);
        clickButtonPublish();
    }
}

