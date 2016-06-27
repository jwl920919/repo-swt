package com.shinwootns.swt.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shinwootns.swt.data.entity.AuthType;

public interface AuthTypeRepository extends JpaRepository<AuthType, Integer> {
}
