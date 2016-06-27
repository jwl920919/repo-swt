package com.shinwootns.swt.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shinwootns.swt.data.entity.SiteInfo;

@Repository
public interface SiteInfoRepository extends JpaRepository<SiteInfo, Integer>{
}
