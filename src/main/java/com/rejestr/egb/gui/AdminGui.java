package com.rejestr.egb.gui;

import com.rejestr.egb.entity.AppUser;
import com.rejestr.egb.login.UserRole;
import com.rejestr.egb.repository.AppUserRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Route(value = "Admin-User")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class AdminGui  extends VerticalLayout {

    TextField userName;
    TextField password;
    ComboBox<UserRole> role;
    ComboBox<AppUser> users;
    Button addButton;
    Button removeButton;
    Button loadUsers;
    HorizontalLayout horizontalLayout = new HorizontalLayout();

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AdminGui(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;

        userName = new TextField();
        password = new TextField();
        role = new ComboBox("rola");
        role.setItems(UserRole.values());
        users = new ComboBox("użytkownik");
        userName.setPlaceholder("nazwa");
        password.setPlaceholder("hasło");
        addButton = new Button("Dodaj");
        removeButton = new Button("Usuń");
        loadUsers = new Button("Załaduj istniejących");
        horizontalLayout.add(addButton,loadUsers,removeButton);
        configaAddButton();
        configaLoadButton();
        configaRemoveButton();
        add(userName,password,role,users, horizontalLayout);
    }

    private void configaLoadButton() {
        loadUsers.addClickListener(event -> {
            List<AppUser> all = appUserRepo.findAll();
            users.setItems(all);
            users.setItemLabelGenerator(item -> item.getUsername());
        });
    }

    private void configaAddButton() {
        addButton.addClickListener(event -> {
            AppUser user = new AppUser();

        user.setUsername(userName.getValue());
        user.setPassword(passwordEncoder.encode(password.getValue()));
        user.setRole(role.getValue());

        appUserRepo.save(user);
        });
    }

    private void configaRemoveButton() {
        removeButton.addClickListener(event -> {
        if(!users.isEmpty()) {
            appUserRepo.delete(users.getValue());
        }
        });
    }


}
