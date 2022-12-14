package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.entities.Hive;
import io.github.edsonisaac.beemonitor.entities.Mensuration;
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

    private final HiveService hiveService;
    private final MensurationService mensurationService;
    private final UserService userService;

    @Autowired
    public FacadeService(
            HiveService hiveService,
            MensurationService mensurationService,
            UserService userService
    ) {
        this.hiveService = hiveService;
        this.mensurationService = mensurationService;
        this.userService = userService;
    }

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
     * Mensuration find all by hive list.
     *
     * @param hiveId the hive id
     * @return the list
     */
    public void mensurationDeleteByHive(UUID hiveId) {
        mensurationService.deleteByHive(hiveId);
    }

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

    /**
     * Mensuration save mensuration.
     *
     * @param mensuration the mensuration
     * @return the mensuration
     */
    public Mensuration mensurationSave(Mensuration mensuration) {
        return mensurationService.save(mensuration);
    }

    /////////////////////////////////////////////////// USER ///////////////////////////////////////////////////

    /**
     * User find all list.
     *
     * @return the list
     */
    public List<UserProjection> userFindAll() {
        return userService.findAll();
    }

    /**
     * User find by id user.
     *
     * @param id the id
     * @return the user
     */
    public User userFindById(UUID id) {
        return userService.findById(id);
    }

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