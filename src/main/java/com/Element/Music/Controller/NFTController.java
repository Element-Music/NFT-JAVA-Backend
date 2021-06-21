package com.Element.Music.Controller;

import com.Element.Music.Model.DAO.NFTDAO.NFT;
import com.Element.Music.Service.NFTService;
import org.apache.maven.shared.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/price")

public class NFTController {
    private final NFTService nftService;

    public NFTController(NFTService nftService) { this.nftService = nftService; }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addPrice(HttpServletRequest req){
        JSONObject jsonObject = new JSONObject();
        String tokenId = req.getParameter("tokenId").trim();
        String originalPrice = req.getParameter("originalPrice").trim();
        String rate = req.getParameter("rate").trim();

        if (tokenId.equals("")) {
            jsonObject.put("code", 2);
            jsonObject.put("msg", "nft token或者价格为空");
            return jsonObject;
        }

        int addNFTRes = nftService.initializePrice(Long.parseLong(tokenId), StringUtils.isNotBlank(originalPrice) ?
                        Double.parseDouble(originalPrice) : 0.0,
                StringUtils.isNotBlank(rate) ? Double.parseDouble(rate) : 0);
        if (addNFTRes == 0) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "增加nft价格成功");
        } else {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "增加nft价格失败, 该tokenId已存在");
        }
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updatePrice(HttpServletRequest req){
        JSONObject jsonObject = new JSONObject();
        String tokenId = req.getParameter("tokenId").trim();
        String updatedPrice = req.getParameter("price").trim();

        if (tokenId.equals("") || updatedPrice.equals("")) {
            if (tokenId.equals("")) {
                jsonObject.put("code", 2);
                jsonObject.put("msg", "Token Id为空");
            } else {
                jsonObject.put("code", 4);
                jsonObject.put("msg", "输入价格为空");
            }
            return jsonObject;
        }
        int res = nftService.updateNFTPrice(Long.parseLong(tokenId), Double.parseDouble(updatedPrice));
        if(res == 1) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "该nft不存在");
        }else if(res == 0) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "更改nft价钱成功");
        }else{
            jsonObject.put("code", 3);
            jsonObject.put("msg", "更改nft价钱失败，系统错误");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/getByTokenId", method = RequestMethod.GET)
    public Object PriceOfSongId(HttpServletRequest req) {
        String tokenId = req.getParameter("tokenId");
        return nftService.getNFTByTokenId(Long.parseLong(tokenId));
    }

    @RequestMapping(value="/all", method=RequestMethod.GET)
    public Object allPrice() {
        return nftService.getAllNFTPrice();
    }
} 
