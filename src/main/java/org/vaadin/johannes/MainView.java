package org.vaadin.johannes;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@Push
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service The message service. Automatically injected Spring managed bean.
     */
    public MainView(@Autowired GreetService service) {
        DataProvider<String, Void> dataProvider1 = DataProvider.fromCallbacks(query -> {
            return service.loadGreets(query.getLimit(), query.getOffset()).stream();
        }, query -> {
            return GreetService.GREET_COUNT;
        });
        DataProvider<String, Void> dataProvider2 = DataProvider.fromCallbacks(query -> {
            query.getLimit();
            query.getOffset();
            return service.loadGreets(query.getLimit(), query.getOffset()).stream();
        }, query -> {
            return GreetService.GREET_COUNT;
        });

        ComboBox<String> greetComboBox1 = new ComboBox<>();
        greetComboBox1.setDataProvider((DataProvider)dataProvider1);

        RouterLink navi = new RouterLink("Navigate", SecondView.class);
        add(navi, greetComboBox1);
    }
}
