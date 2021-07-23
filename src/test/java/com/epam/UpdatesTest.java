package com.epam;


import com.epam.cfg.HibernateTestConfiguration;
import com.epam.dto.RequestDto;
import com.epam.dto.UserDto;
import com.epam.service.RequestService;
import com.epam.service.UserService;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateTestConfiguration.class})
public class UpdatesTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    private UserDto user = new UserDto();

    private RequestDto requestDto = new RequestDto();

    private RequestDto requestDto2 = new RequestDto();

    private List<RequestDto> requestDtos = new ArrayList<>();

    private String nameAdmin;

    @Before
    public void setUp() {
        nameAdmin = "admin";
        user.setName(nameAdmin);
        user.setRole("ADMIN");
    }

    @Test
    public void shouldCreateUserAddRequestsAndUpdateUser() {

        UserDto userDto = userService.create(user);

        requestDto.setName("test1");
        requestDto.setUserId(userDto.getId());

        requestDto2.setName("test2");
        requestDto2.setUserId(userDto.getId());

        requestDtos.add(requestDto);
        requestDtos.add(requestDto2);

        userDto.setRequests(requestDtos);

        userService.update(userDto);

        UserDto result = userService.get(nameAdmin);

        Assert.assertEquals(userDto.getId(), result.getId());
    }

    @Test
    public void shouldFindAllRequestsByUser() {
        UserDto userDto = userService.get(nameAdmin);

        List<RequestDto> byUserId = requestService.getByUserId(userDto.getId());

        Assert.assertThat(byUserId, Matchers.hasSize(2));
    }


    @Test
    public void shouldUpdateRequest() {
        UserDto userDto = userService.get(nameAdmin);

        List<RequestDto> byUserId = requestService.getByUserId(userDto.getId());
        RequestDto requestDto = byUserId.get(1);

        RequestDto oldRequest = new RequestDto();
        oldRequest.setId(requestDto.getId());
        oldRequest.setCost(requestDto.getCost());
        oldRequest.setName(requestDto.getName());
        oldRequest.setUserId(requestDto.getUserId());

        requestDto.setName("updatedName");
        requestDto.setCost(new BigDecimal(777));

        RequestDto result = requestService.update(requestDto);

        Assert.assertThat(oldRequest, Matchers.not(result));
    }

    @After
    public void deleteCreatedUsers(){
        userService.delete(user.getId());
    }

}
