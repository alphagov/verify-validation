package uk.gov.ida.validation.messages;

import org.junit.Test;

import java.text.MessageFormat;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static uk.gov.ida.validation.messages.MessageImpl.fieldMessage;
import static uk.gov.ida.validation.messages.MessageImpl.globalMessage;

/**
 * Unit tests for {@link @{@link MessageImpl}.
 */
public class MessageImplTest {
    @Test
    public void constructorWithCode() {
        // Given
        Object param1 = new Object();
        Object param2 = new Object();

        // When
        MessageImpl message = new MessageImpl("theField",
                "theCode",
                "theParameterisedMessage",
                param1, param2);

        // Then
        assertThat(message.getField(), is("theField"));
        assertThat(message.getCode(), is("theCode"));
        assertThat(message.getParameterisedMessage(), is("theParameterisedMessage"));

        assertThat(message.getMessageParameters(), notNullValue());
        assertThat(message.getMessageParameters().length, is(2));
        assertThat(message.getMessageParameters()[0], sameInstance(param1));
        assertThat(message.getMessageParameters()[1], sameInstance(param2));
    }

    @Test
    public void globalMessageWithCodeAndParameterisedMessage() {
        // When
        MessageImpl message = globalMessage("theCode", "theParameterisedMessage");

        // Then
        assertThat(message.getField(), nullValue());
        assertThat(message.getCode(), is("theCode"));
        assertThat(message.getParameterisedMessage(), is("theParameterisedMessage"));

        assertThat(message.getMessageParameters(), nullValue());
    }

    @Test
    public void globalMessageWithCodeParameterisedMessageAndParameters() {
        // Given
        Object param1 = new Object();

        // When
        MessageImpl message = globalMessage("theCode",
                "theParameterisedMessage",
                param1);

        // Then
        assertThat(message.getField(), nullValue());
        assertThat(message.getCode(), is("theCode"));
        assertThat(message.getParameterisedMessage(), is("theParameterisedMessage"));

        assertThat(message.getMessageParameters(), notNullValue());
        assertThat(message.getMessageParameters().length, is(1));
        assertThat(message.getMessageParameters()[0], sameInstance(param1));
    }

    @Test
    public void fieldMessageWithFieldCodeAndParameterisedMessage() {
        // When
        MessageImpl message = fieldMessage(
                "theField",
                "theCode",
                "theParameterisedMessage");

        // Then
        assertThat(message.getField(), is("theField"));
        assertThat(message.getCode(), is("theCode"));
        assertThat(message.getParameterisedMessage(), is("theParameterisedMessage"));

        assertThat(message.getMessageParameters(), nullValue());
    }

    @Test
    public void fieldMessageWithFieldCodeParameterisedMessageAndParameters() {
        // Given
        Object param1 = new Object();

        // When
        MessageImpl message = fieldMessage(
                "theField",
                "theCode",
                "theParameterisedMessage",
                param1);

        // Then
        assertThat(message.getField(), is("theField"));
        assertThat(message.getCode(), is("theCode"));
        assertThat(message.getParameterisedMessage(), is("theParameterisedMessage"));

        assertThat(message.getMessageParameters(), notNullValue());
        assertThat(message.getMessageParameters().length, is(1));
        assertThat(message.getMessageParameters()[0], sameInstance(param1));
    }

    @Test
    public void renderMessage() {
        // Given
        Object param1 = "This is param1";
        Object param2 = 1234L;

        // When
        MessageImpl message = fieldMessage(
                "theField",
                "theCode",
                "The message param1=[{0}] and param2=[{1}]",
                param1, param2);

        // Then
        assertThat(message.getRenderedMessage(), is(MessageFormat.format(message.getParameterisedMessage(), param1, param2)));
    }

    @Test
    public void equalsWithSameInstance() {
        // Given
        MessageImpl message = globalMessage("theCode", "theMessage");

        // Then
        assertThat(message.equals(message), is(true));
    }

    @Test
    public void equalsWithAnotherType() {
        // Given
        MessageImpl message = globalMessage("theCode", "theMessage");

        // Then
        assertThat(message.equals(new Object()), is(false));
    }

    @Test
    public void equalsWithNull() {
        // Given
        MessageImpl message = globalMessage("theCode", "theMessage");

        // Then
        assertThat(message.equals(null), is(false));
    }

    @Test
    public void equalsOther() {
        // Given
        Object param1 = "This is param1";
        Object param2 = 1234L;

        // Then
        assertThat(fieldMessage("theField","theCode", "theMessage", param1),
            equalTo(fieldMessage("theField","theCode", "theMessage", param1)));
    }

    @Test
    public void hashCodeTest() {
        // Given
        Object param1 = "This is param1";
        Object param2 = 1234L;
        MessageImpl message = fieldMessage("theField","theCode", "theMessage", param1);

        // Then
        assertThat(message.hashCode(), equalTo(Objects.hash(message.getField(),
                message.getCode(),
                message.getParameterisedMessage(),
                message.getMessageParameters())));
    }

    @Test
    public void toStringTest() {
        // Given
        Object param1 = "This is param1";
        Object param2 = 1234L;
        MessageImpl message = fieldMessage("theField","theCode", "theMessage", param1);

        // Then
        assertThat(message.toString(), containsString("field=theField"));
        assertThat(message.toString(), containsString("code=theCode"));
        assertThat(message.toString(), containsString("parameterisedMessage=theMessage"));
        assertThat(message.toString(), containsString("messageParameters={This is param1}"));
    }
}