package com.tentelemed.archipel.security.infrastructure.persistence;

import com.tentelemed.archipel.security.infrastructure.persistence.domain.RoleQ;
import com.tentelemed.archipel.security.infrastructure.persistence.domain.UserQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Classe utilitaire générée automatiquement par Spring DATA
 * Utilisée par le UserRepository pour fournir des implémentations par default des requetes les plus standards
 * <p/>
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 12:20
 */
public interface RoleRepositoryUtil extends JpaRepository<RoleQ, String> {

}
