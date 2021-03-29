package ssafy.a306.vnc.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ssafy.a306.vnc.entity.VoteVo;
import ssafy.a306.vnc.model.VoteDto;
import ssafy.a306.vnc.model.service.VoteService;

@RestController
@RequestMapping("/vote")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VoteController {

	@Autowired
	VoteService voteService;

	@PostMapping(value = "/create")
	public ResponseEntity<VoteVo> create(@RequestBody VoteVo vote) {
		ResponseEntity<VoteVo> entity = null;
		try {
			SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
			String hashKey = getHash(format1.format (System.currentTimeMillis())).substring(0,9);
			vote.setHashKey(hashKey);
			entity = new ResponseEntity<VoteVo>(voteService.save(vote), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

//	@GetMapping(value = "/read")
//	public ResponseEntity read(@RequestParam String type, @RequestParam int category) {
//
//		ResponseEntity entity = null;
//		Map result = new HashMap();
//		List<VoteVo> list;
//		if (type.equals("")) {
//			list = voteService.findAll();
////        System.out.println(list);
//			if (list != null) {
//				result.put("list", list);
//				result.put("success", "success");
//				entity = new ResponseEntity(result, HttpStatus.OK);
////            System.out.println(entity);
//			} else {
//				result.put("success", "fail");
//				entity = new ResponseEntity(result, HttpStatus.OK);
//			}
//		}else if(type.equals("category")) {
//			list = voteService.findByCategory(category);
//			if (list != null) {
//				result.put("list", list);
//				result.put("success", "success");
//				entity = new ResponseEntity(result, HttpStatus.OK);
////            System.out.println(entity);
//			} else {
//				result.put("success", "fail");
//				entity = new ResponseEntity(result, HttpStatus.OK);
//			}
//		}
//		return entity;
//	}
	
	
	private String getHash(String str) {
		String hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes());
			byte byteData[] = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < byteData.length; i++)
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			hash = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			hash = null;
		}
		return hash;
	}
	
}
