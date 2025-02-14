package com.ynov.service.impl;

import com.ynov.component.QuizzMapper;
import com.ynov.dto.QuizzDto;
import com.ynov.model.Quizz;
import com.ynov.model.User;
import com.ynov.repository.QuizzRepository;
import com.ynov.repository.UserRepository;
import com.ynov.service.IQuizzService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
@Transactional
public class QuizzServiceImpl implements IQuizzService {
    private final QuizzRepository quizzRepository;
    private final UserRepository userRepository;

    @Override
    public List<QuizzDto> getQuizzesByUser(String userId) {
        return quizzRepository.findByUserId(userId)
                .stream()
                .map(QuizzMapper::mapQuizzToQuizzDto)
                .collect(Collectors.toList());
    }
    @Override
    public Long createQuizz(QuizzDto quizzDto, String UID) {
        Optional<User> userSearched = userRepository.findUserByUID(UID);
        if (userSearched.isPresent()) {
            Quizz quizz = QuizzMapper.mapQuizzDtoToQuizz(quizzDto);
            quizz.setUser(userSearched.get());
            quizzRepository.persist(quizz);
            log.info("Quizz N {} {} created :).", quizz.id, quizz.getTitle());
            return quizz.id;
        }
        else {
            log.error("User not found :(");
            throw new WebApplicationException("User not found", Response.Status.NOT_FOUND);
        }
    }
}
