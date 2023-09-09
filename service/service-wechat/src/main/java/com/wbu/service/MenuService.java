package com.wbu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.model.wechat.Menu;
import com.wbu.vo.wechat.MenuVo;

import java.util.List;

/**
* @author 11852
* @description 针对表【menu(订单明细 订单明细)】的数据库操作Service
* @createDate 2023-09-08 17:03:40
*/
public interface MenuService extends IService<Menu> {

    List<Menu> findMenuOneInfo();

    List<MenuVo> findMenuInfo();

    void syncMenu();

    void removeMenu();

}
