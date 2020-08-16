package com.messengerapphiber.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.messengerapphiber.dao.LinkDao;
import com.messengerapphiber.dao.LinkDaoImpl;
import com.messengerapphiber.exception.DataNotFoundException;
import com.messengerapphiber.model.Link;
import com.messengerapphiber.model.Message;
import com.messengerapphiber.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageReource {
	
	LinkDao lDao = new LinkDaoImpl();

	MessageService msd = new MessageService();

	@GET
	public Response getMess(@QueryParam("year") int year,@QueryParam("start") int start,@QueryParam("size") int size) {
		/*
		 * if(year>0) {
		 * 
		 * return Response.status(Status.OK).entity( msd.messagesbyYear(year)).build();
		 * } else if(start>=0 && size>=0) {
		 * 
		 * return Response.status(Status.OK).entity(msd.pagingMeth(start,
		 * size)).build(); } else
		 */
		List<Message> lisMe =new ArrayList<>();

		 if(year>0) {
		  
			 lisMe=msd.messagesbyYear(year);
			 if(lisMe == null)
			 {
				 return Response.status(Status.NOT_FOUND).entity("no data for this year "+year ).build();
			 }else
			 {
			 
		  return Response.status(Status.OK).entity(lisMe ).build();}
		  } 
		 System.out.println("start"+start + "size"+size);
		 if(start>0 && size>0) {
			 
			 lisMe=msd.pagingMeth(start,size);
			 if(lisMe == null)
			 {
				 return Response.status(Status.NOT_FOUND).entity("no data for this values  "+ start + " "+size).build();
			 }else {
			 
			  return Response.status(Status.OK).entity(lisMe).build();}
		 }
		return  Response.status(Status.OK).entity(msd.getAllMess()).build();
	}

	@GET
	@Path("/{messageId}")
	public Response getMessId(@PathParam("messageId") int messageId,@Context UriInfo uriInfo) {
		Message med = msd.getMess(messageId);
		
		if(med == null)
		{
			 return Response.status(Status.NOT_FOUND).entity("no data for this value  "+ messageId ).build();
		}else
		{
		String uri = uriInfo.getBaseUriBuilder().path(MessageReource.class).path(Integer.toString(messageId)).build().toString();
		String profUri = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(med.getAuthor()).toString();
		
		String comUri = uriInfo.getBaseUriBuilder().path(MessageReource.class)
				.path(MessageReource.class,"getCommentRes")
				.path(CommentResource.class).resolveTemplate("messageId", med.getMessageId()).toString();
		/*
		 * if(med == null) {
		 * 
		 * throw new WebApplicationException(Status.NOT_FOUND); //return
		 * Response.status(Status.INTERNAL_SERVER_ERROR).
		 * entity("Data not available for  "+messageId+" id").build();// //throw new
		 * DataNotFoundException("data not found for the id "+messageId); }
		 */
		Link link = new Link();
		link.setLinks(uri);
		link.setRel("Self");
		Link profLink = new Link();
		Link commLink = new Link();
		profLink.setLinks(profUri);
		profLink.setRel("Profile");
		commLink.setLinks(comUri);
		commLink.setRel("comments");
		
		link.setMessLinkFk(messageId);
		List<Link> ls = new ArrayList<Link>();
		ls.add(link);
		ls.add(profLink);
		ls.add(commLink);
		med.setLinks(ls);
		lDao.save(link);
		
		/*
		 * if(med==null) { return Response.status(Status.NOT_FOUND).
		 * entity("Data not available for  "+messageId+" id").build(); }
		 */
		return //med;
		
		Response.status(Status.OK).entity(med).build();}

	}

	@POST
	public Response addMess(Message message,@Context UriInfo  uriInfo) {

		System.out.println("entering controller");
		
		if(message == null)
		{
			 return Response.status(Status.NOT_FOUND).entity("Enter correct details ").build();
			
		}else {
		 msd.saveMess(message);
		//String mId=String.valueOf(mmg.getMessageId());
		//System.out.println("value is "+mId);
		//String path=uriInfo.getAbsolutePath()+mId;
		//URI uri = uriInfo.getAbsolutePathBuilder().path(mId).build();

		//Response.status(Status.CREATED).entity(x).build();
		
		return Response.status(Status.CREATED).entity(" Message Added Successfully").build();
		
				
		}	//Response.created(uri).entity(x).build();
		

	}

	@PUT
	@Path("/{messageId}")
	public Response updateMess(@PathParam("messageId") int messageId, Message message) {
		Message md = msd.getMess(messageId);

		if(message == null && md == null)
		{
			return Response.status(Status.NOT_FOUND).entity("Enter correct details ").build();
			
		}else {
		// System.out.println("date in");
		message.setMessageId(messageId);

		message.setCreated(md.getCreated());

		msd.updateMess(message);

		return Response.status(Status.OK).entity(" Message Updated Successfully").build();

		}
		}

	@DELETE
	@Path("/{messageId}")
	public Response delMessId(@PathParam("messageId") int messageId) {
		
		if(messageId > 0)
		{
		msd.deleMess(messageId);

		return Response.status(Status.OK).entity(" Message  with id "+ messageId+"Deleted Successfully").build();
		}else
		{
			return Response.status(Status.NOT_FOUND).entity("no data found for this id "+messageId).build();
		}
	}
	
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentRes()
	{
		return new CommentResource();
	}

}
