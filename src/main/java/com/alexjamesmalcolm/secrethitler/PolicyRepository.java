package com.alexjamesmalcolm.secrethitler;

import com.alexjamesmalcolm.secrethitler.policies.Policy;
import org.springframework.data.repository.CrudRepository;

public interface PolicyRepository extends CrudRepository<Policy, Long> {
}
