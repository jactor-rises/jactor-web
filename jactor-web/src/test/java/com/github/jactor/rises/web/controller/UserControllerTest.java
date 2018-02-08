package com.github.jactorrises.web.controller;

import com.github.jactorrises.client.datatype.UserName;
import com.github.jactorrises.client.domain.User;
import com.github.jactorrises.client.facade.UserFacade;
import com.github.jactorrises.web.dto.UserDto;
import com.github.jactorrises.web.html.ParameterConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserFacade userFacadeMock;

    @InjectMocks
    private UserController testUserController;

    @Test public void shouldNotFetchUserByUserNameIfTheUserNameInTheWebRequestIsNullOrAnEmptyString() {
        WebRequest webRequestMock = mock(WebRequest.class);
        when(webRequestMock.getParameter(ParameterConstants.CHOOSE_USER)).thenReturn(null);

        testUserController.doUser(mock(ModelMap.class), webRequestMock);

        verify(userFacadeMock, never()).findUsing(any(UserName.class));
        when(webRequestMock.getParameter(ParameterConstants.CHOOSE_USER)).thenReturn(" \n \t");

        testUserController.doUser(mock(ModelMap.class), webRequestMock);

        verify(userFacadeMock, never()).findUsing(any(UserName.class));
    }

    @Test public void shouldFetchTheUserIfChooseParameterExist() {
        WebRequest mockedWebRequest = mock(WebRequest.class);
        ModelMap mockedModelMap = mock(ModelMap.class);
        User mockedUser = mock(User.class);

        when(mockedWebRequest.getParameter(ParameterConstants.CHOOSE_USER)).thenReturn("user");
        when(userFacadeMock.findUsing(new UserName("user"))).thenReturn(Optional.of(mockedUser));

        testUserController.doUser(mockedModelMap, mockedWebRequest);

        verify(mockedModelMap, atLeastOnce()).put(eq(ControllerValues.ATTRIBUTE_USER), any(UserDto.class));
    }

    @Test public void shouldNotPutTheUserOnTheModelIfNotFound() {
        WebRequest mockedWebRequest = mock(WebRequest.class);
        ModelMap mockedModelMap = mock(ModelMap.class);

        testUserController.doUser(mockedModelMap, mockedWebRequest);

        verify(mockedModelMap, never()).put(eq(ControllerValues.ATTRIBUTE_USER), any(UserDto.class));
    }
}
