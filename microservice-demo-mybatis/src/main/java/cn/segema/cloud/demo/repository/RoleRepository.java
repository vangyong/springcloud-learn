package cn.segema.cloud.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.segema.cloud.demo.domain.DemoRole;

@Repository
public interface RoleRepository extends JpaRepository<DemoRole, String> {
	
	 
	 @Query("select r from Role r where r.roleName = ?1") 
	 List<DemoRole> findByRoleName(String roleName); 
	 
	 
}
