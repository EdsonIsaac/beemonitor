package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.entities.Hive;
import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.projections.HiveProjection;
import io.github.edsonisaac.beemonitor.projections.MensurationProjection;
import io.github.edsonisaac.beemonitor.projections.UserProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The type Facade service.
 *
 * @author Edson Isaac
 */
@Service
public class FacadeService {

    @Autowired private HiveService hiveService;
    @Autowired private MensurationService mensurationService;
    @Autowired private UserService userService;

    /////////////////////////////////////////////////// HIVE ///////////////////////////////////////////////////

    /**
     * Hive delete.
     *
     * @param id the id
     */
    public void hiveDelete(UUID id) {
        hiveService.delete(id);
    }

    /**
     * Hive find all list.
     *
     * @return the list
     */
    public List<HiveProjection> hiveFindAll() {
        return hiveService.findAll();
    }

    /**
     * Hive find by code hive projection.
     *
     * @param code the code
     * @return the hive projection
     */
    public HiveProjection hiveFindByCode (String code) {
        return hiveService.findByCode(code);
    }

    /**
     * Hive find by id hive.
     *
     * @param id the id
     * @return the hive
     */
    public Hive hiveFindById (UUID id) {
        return hiveService.findById(id);
    }

    /**
     * Hive save hive.
     *
     * @param hive the hive
     * @return the hive
     */
    public Hive hiveSave (Hive hive) {
        return hiveService.save(hive);
    }

    /////////////////////////////////////////////////// MENSURATION ///////////////////////////////////////////////////

    /**
     * Mensuration find by hive id with size list.
     *
     * @param hiveId the hive id
     * @param size   the size
     * @return the list
     */
    public List<MensurationProjection> mensurationFindByHiveIdWithSize(UUID hiveId, Integer size) {
        return mensurationService.findByHiveIdWithSize(hiveId, size);
    }

    /////////////////////////////////////////////////// USER ///////////////////////////////////////////////////

    /**
     * User find by username user projection.
     *
     * @param username the username
     * @return the user projection
     */
    public UserProjection userFindByUsername (String username) {
        return userService.findByUsername(username);
    }

    /**
     * User save user.
     *
     * @param user the user
     * @return the user
     */
    public User userSave (User user) {
        return userService.save(user);
    }
}