package store.ckorovoda;

import io.quarkus.panache.common.Sort;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/vote")
public class VoteResource {

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<Participant> getAllParticipants() {
        return Participant.listAll(Sort.descending("score"));
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createParticipant(@NotNull @Valid Participant participant) {
        Participant.persist(participant);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Participant updateParticipant(@PathParam("id") Long id, Participant participant) {
        Participant p = Participant.findById(id);
        if (p == null) {
            throw new NotFoundException();
        }
        if (participant.getScore() != 0) {
            p.setScore(participant.getScore());
        }
        if (participant.getDays() != 0) {
            p.setDays(participant.getDays());
        }

        return p;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void removeParticipant(@PathParam("id") Long id) {
        Participant p = Participant.findById(id);
        if (p == null) {
            throw new NotFoundException();
        }
        p.delete();
    }
}