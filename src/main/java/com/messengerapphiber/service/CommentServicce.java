package com.messengerapphiber.service;

import java.util.List;

import com.messengerapphiber.dao.CommentDao;
import com.messengerapphiber.dao.CommentdaoImpl;
import com.messengerapphiber.model.Comment;
import com.messengerapphiber.model.Message;

public class CommentServicce {
	
	CommentDao cmDao = new CommentdaoImpl();
	
	public Comment getAllComms(int messId){
		
		Comment lisd = cmDao.getComms(messId);

		return lisd;}
		

	public int saveCom(Comment comment)
	{
		cmDao.saveComms(comment);
		
		return 0;
	}
	
	public int updateComm(Comment comment)
	{
		cmDao.updateComms(comment);
		return 0;
	}
	
	public int deleComm(int messId,int commId)
	{
		cmDao.deleComms(messId, commId);
		return 0;
	}
	
}
