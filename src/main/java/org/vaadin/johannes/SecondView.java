package org.vaadin.johannes;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

@Route("second")
@Push
public class SecondView extends VerticalLayout {
    private FeederThread thread;

    public SecondView(@Autowired GreetService service) {
        DataProvider<String, Void> dataProvider1 = DataProvider.fromCallbacks(query -> {
            return service.loadGreets(query.getLimit(), query.getOffset()).stream();
        }, query -> {
            return GreetService.GREET_COUNT;
        });

        ComboBox<String> greetComboBox1 = new ComboBox<>();
        greetComboBox1.setDataProvider((DataProvider)dataProvider1);

        RouterLink navi = new RouterLink("Navigate", MainView.class);
        add(navi, greetComboBox1);

        Grid<Person> grid = new Grid<>();
        PersonService personService = new PersonService();

        /*
         * This Data Provider doesn't load all items into the memory right away.
         * Grid will request only the data that should be shown in its current
         * view "window". The Data Provider will use callbacks to load only a
         * portion of the data.
         */
        CallbackDataProvider<Person, Void> provider = DataProvider
                .fromCallbacks(query -> personService
                                .fetch(query.getOffset(), query.getLimit()).stream(),
                        query -> personService.count());
        grid.setDataProvider(provider);

        grid.addColumn(Person::getName).setHeader("First Name");
        grid.addColumn(Person::getId).setHeader("Id");
        grid.addColumn(Person::getAge).setHeader("Age");

        add(grid);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        add(new Span("Waiting for updates"));

        // Start the data feed thread
        thread = new FeederThread(attachEvent.getUI(), this);
        thread.start();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        // Cleanup
        thread.interrupt();
        thread = null;
    }

    private static class FeederThread extends Thread {
        private final UI ui;
        private final SecondView view;

        private int count = 0;

        public FeederThread(UI ui, SecondView view) {
            this.ui = ui;
            this.view = view;
        }

        @Override
        public void run() {
            try {
                // Update the data for a while
                while (count < 20) {
                    // Sleep to emulate background work
                    Thread.sleep(1500);
                    String message = "This is update " + count++;

                    ui.access(() -> view.add(new Span(message)));
                }

                // Inform that we are done
                ui.access(() -> {
                    view.add(new Span("Done updating"));
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
