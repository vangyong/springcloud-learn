package cn.segema.cloud.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.segema.cloud.demo.common.Pager;
import cn.segema.cloud.demo.domain.User;
import cn.segema.cloud.demo.repository.UserRepository;
import cn.segema.cloud.demo.vo.UserPersonalVO;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	/**
	 * 注：@GetMapping("/{id}")是spring 4.3的新注解等价于：
	 * 
	 * @RequestMapping(value = "/id", method = RequestMethod.GET)
	 *                       类似的注解还有@PostMapping等等
	 * @param id
	 * @return user信息
	 */
	@GetMapping("/{userId}")
	public User findById(@PathVariable String userId) {
		Optional<User> findOne = this.userRepository.findById(userId);
		return findOne.get();
	}

	@GetMapping("/list")
	public List<User> list(User user, Model model) {
		List<User> userList = userRepository.findAll();
		return userList;
	}

	@PostMapping("/add")
	public User add(User user, Model model) {
		if (user.getUserId() == null || "".equals(user.getUserId())) {
			user.setUserId(UUID.randomUUID().toString());
		}
		userRepository.save(user);
		return user;
	}

	@RequestMapping(value = "edit")
	public User edit(User user, Model model) {
		// Role oldRole = roleRepository.getOne(role.getRoleId());
		// BeanUtils.copyProperties(role, oldRole);
		userRepository.save(user);
		return user;
	}

	@RequestMapping(value = "delete")
	public User delete(User user) {
		userRepository.delete(user);
		return user;
	}

	@GetMapping("/listUserPersonalByUserName/{userName}")
	public List<UserPersonalVO> listUserPersonalByUserName(@PathVariable String userName) {
		List<UserPersonalVO> userList = userRepository.findUserPersonalByUserName(userName);
		return userList;
	}

//  @GetMapping("/listByPage/{page}/{size}")
//	public Page<User> listByPage(@PathVariable Integer page,@PathVariable Integer size) {
//		Sort sort = new Sort(Direction.DESC, "userId");
//		Pageable pageable = new PageRequest(page, size, sort);
//		return userRepository.findAll(pageable);
//	}
	@GetMapping("/listByPage")
	public Pager<User> listByPage() {
		Sort sort = Sort.by(Direction.DESC, "userId");
		Pageable pageable = PageRequest.of(0, 30, sort);
		Page<User> page = userRepository.findAll(pageable);
		Pager<User> pager = new Pager<User>();
		pager.setCode("0");
		pager.setMsg("success");
		pager.setCount(page.getTotalElements());
		pager.setData(page.getContent());
		return pager;
	}

}
