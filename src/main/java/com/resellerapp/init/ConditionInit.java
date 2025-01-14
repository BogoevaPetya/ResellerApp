package com.resellerapp.init;

import com.resellerapp.model.entity.Condition;
import com.resellerapp.model.enums.ConditionName;
import com.resellerapp.repository.ConditionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ConditionInit implements CommandLineRunner {
    private final ConditionRepository conditionRepository;

    public ConditionInit(ConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean hasConditions = this.conditionRepository.count() > 0;

        if (!hasConditions){
            List<Condition> conditions = new ArrayList<>();

            Arrays.stream(ConditionName.values())
                    .forEach(
                            conditionName -> {
                                Condition condition = new Condition();

                                condition.setName(conditionName);
                                condition.setDescription(conditionName);

                                conditions.add(condition);
                            });

            this.conditionRepository.saveAll(conditions);
        }
    }

}
