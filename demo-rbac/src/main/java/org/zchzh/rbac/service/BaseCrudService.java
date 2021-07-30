package org.zchzh.rbac.service;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author zengchzh
 * @date 2021/5/11
 */
public interface BaseCrudService<ENTITY, ID> {

    /**
     * 获取所有 entity
     * @return 返回 entity list
     */
    List<ENTITY> list();

    /**
     * 获取所有排序后的 entity
     * @param sort 排序规则
     * @return 返回 entity list
     */
    List<ENTITY> list(Sort sort);

    /**
     * 分页获取所有entity
     * @param pageable 分页规则
     * @return 返回分页后的 entity 数据
     */
    Page<ENTITY> list(Pageable pageable);

    /**
     * 条件查询
     * @param example 条件
     * @return 返回 entity list
     */
    List<ENTITY> list(Example<ENTITY> example);

    /**
     * 条件排序查询
     * @param example 条件
     * @param sort 排序规则
     * @return 返回 entity list
     */
    List<ENTITY> list(Example<ENTITY> example, Sort sort);

    /**
     * 分页条件查询
     * @param example 条件
     * @param pageable 分页规则
     * @return 返回 entity list
     */
    Page<ENTITY> list(Example<ENTITY> example, Pageable pageable);

    /**
     * 根据 id 集合获取所有 entity
     * @param ids id 集合
     * @return 返回 entity  list
     */
    List<ENTITY> list(Collection<ID> ids);

    /**
     * 通过 id 获取对应的 entity
     * @param id id
     * @return 返回 Optional entity
     */
    Optional<ENTITY> get(ID id);

    /**
     * 根据传入的 entity 信息 创建该 entity
     * @param entity 传入的 entity 信息
     * @return 返回创建的 entity
     */
    @Transactional(rollbackFor = Exception.class)
    ENTITY create(ENTITY entity);

    /**
     * 根据 entity 集合创建集合中的所有 entity
     * @param entities entity 集合
     * @return 返回创建的 entity 集合
     */
    @Transactional(rollbackFor = Exception.class)
    List<ENTITY> createAll(Collection<ENTITY> entities);

    /**
     * 根据 entity 信息跟新该 entity 信息
     * @param entity 传入的 entity 信息
     * @return 返回跟新的 entity 信息
     */
    @Transactional(rollbackFor = Exception.class)
    ENTITY update(ENTITY entity);

    /**
     * 根据传入的 entity 集合更新该集合中的所有 entity
     * @param entities entity 集合
     * @return 返回更新的 entity 集合
     */
    @Transactional(rollbackFor = Exception.class)
    List<ENTITY> updateAll(Collection<ENTITY> entities);

    /**
     * 根据 id 删除 entity
     * @param id id
     * @return 返回删除的 entity
     */
    @Transactional(rollbackFor = Exception.class)
    ENTITY remove(ID id);

    /**
     * 根据 id 集合删除集合中 id 对应的所有 entity
     * @param ids id 集合
     * @return 返回删除的所有 entity
     */
    @Transactional(rollbackFor = Exception.class)
    List<ENTITY> removeAll(Collection<ID> ids);

    /**
     * 判断跟该 id 对应的 entity 是否存在
     * @param id id
     * @return true 表示存在，false 表示不存在
     */
    boolean isExist(ID id);

    /**
     * 统计表中的数据总数
     * @return 返回统计的总数
     */
    long count();

    /**
     * 将内存中的数据保存到数据库中
     */
    void flush();

}
