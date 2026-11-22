package com.cesul.barber.repository;

import com.cesul.barber.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientsRepository extends JpaRepository<Users, UUID> {
}
