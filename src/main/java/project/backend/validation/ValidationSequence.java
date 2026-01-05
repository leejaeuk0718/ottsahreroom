package project.backend.validation;


import project.backend.validation.ValidationGroups.NotBlankGroups;
import project.backend.validation.ValidationGroups.PatternGroups;
import project.backend.validation.ValidationGroups.RangeGroups;

import jakarta.validation.GroupSequence;

@GroupSequence(value = {NotBlankGroups.class, RangeGroups.class, PatternGroups.class})
public class ValidationSequence {
}
