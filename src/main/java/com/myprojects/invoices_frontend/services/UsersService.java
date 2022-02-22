package com.myprojects.invoices_frontend.services;

import com.myprojects.invoices_frontend.clients.UsersClient;
import com.myprojects.invoices_frontend.domain.Users;
import com.myprojects.invoices_frontend.layout.dialogboxes.ShowNotification;
import com.myprojects.invoices_frontend.mappers.UsersMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private List<Users> usersList;
    private static UsersClient usersClient;
    private static UsersService usersService;
    private static InvoicesService invoicesService = InvoicesService.getInstance();
    private static UsersMapper usersMapper = new UsersMapper();

    public UsersService(UsersClient usersClient) {
        UsersService.usersClient = usersClient;
    }

    public static UsersService getInstance() {
        if (usersService == null) {
            usersService = new UsersService(usersClient);
        }
        return usersService;
    }

    public List<Users> findByFullName(String fullName) {
        return usersList.stream()
                .filter(c -> c.getFullName().contains(fullName))
                .collect(Collectors.toList());
    }

    public List<Users> getUsersList() {
        usersList = usersMapper.mapToUsersList(usersClient.getUsers());
        return usersList;
    }

    public Users getActiveUser() {
        Users user = new Users();
        if(getUsersList().size() > 0) {
           user = usersList.stream()
                    .filter(Users::isActive)
                    .findFirst().get();
        }
        return user;
    }

    public void saveUser(Users user) {
        usersClient.saveUser(usersMapper.mapToUserDto(user));
    }

    public void updateUser(@NotNull Users user) {
        usersClient.updateUser(usersMapper.mapToUserDto(user));
    }

    public void deleteUser(@NotNull Users user) {
        if (invoicesService.getInvoicesList().stream()
                .noneMatch(i -> Objects.equals(i.getUser().getId(), user.getId()))) {
            usersClient.deleteUser(usersMapper.mapToUserDto(user));
        } else {
            ShowNotification cantBeDeleted = new ShowNotification("Nie można usunąć użytkownika "
                    + user.getFullName() + ". Jest z nim powiązana wystawiona faktura!", 5000);
            cantBeDeleted.show();
        }
    }
}