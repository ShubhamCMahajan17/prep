package backend.project.journal.service;

import backend.project.journal.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;


import java.util.stream.Stream;
@Disabled
public class UserArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {

        return Stream.of(
                Arguments.of(User.builder().userName("jam").password("jam").build()),
                Arguments.of(User.builder().userName("suman").password("suman").build())

        );
    }
}
