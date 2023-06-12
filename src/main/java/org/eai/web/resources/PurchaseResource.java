package org.eai.web.resources;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Objects;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import io.quarkus.panache.common.Page;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import org.eai.database.utils.PagedResult;

import org.eai.domain.exceptions.ApiBadRequestException;
import org.eai.domain.dto.PurchaseDto;
import org.eai.domain.services.PurchaseService;

import org.eai.entities.models.PurchaseEntity;

import org.eai.web.models.request.PurchaseDateAndStoreRequest;
import org.eai.web.models.request.PurchaseRequest;
import org.eai.web.utils.ConstantsWeb;

@Path("purchase")
public class PurchaseResource {

    @Inject
    PurchaseService purchaseService;

    @GET
    @Operation(summary = "Get all purchases")
    @APIResponse(responseCode = "200", description = "Get all purchases", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = PurchaseDto.class)))
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPurchases() {
        List<PurchaseEntity> purchaseList = purchaseService.getAllPurchases();
        return Response.ok(purchaseList).build();
    }

    @GET
    @Path("/paginated")
    @Operation(summary = "Get paginated purchases")
    @APIResponse(responseCode = "200", description = "Get purchases paginated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResult.class)))
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPurchasesPagedList(
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize) {
        Page page = Page.of(pageIndex, pageSize);
        PagedResult<PurchaseDto> result = purchaseService.getPurchases(page);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/list/{store}")
    @Operation(summary = "Get purchases list by store")
    @APIResponse(responseCode = "200", description = "Get purchases list by store", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PurchaseDto.class)))
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPurchasesListByStore(@PathParam("store") String store) {
        List<PurchaseDto> result = purchaseService.getPurchaseByStore(store);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/listByDatePurchaseAndStore")
    @Operation(summary = "Get purchases list by purchase date and store")
    @APIResponse(responseCode = "200", description = "Get purchases list by purchase date and store", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PurchaseDto.class)))
    @APIResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiBadRequestException.class)))
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPurchasesListByDatePurchaseAndStore(PurchaseDateAndStoreRequest purchaseDateAndStoreRequest) throws ApiBadRequestException {

        if (Objects.isNull(purchaseDateAndStoreRequest)
                || Objects.isNull(purchaseDateAndStoreRequest.getDatePurchase())){
            throw new ApiBadRequestException(ConstantsWeb.INVALID_PURCHASE_REQUEST);
        }

        List<PurchaseDto> result = purchaseService.purchaseByDateAndStore(purchaseDateAndStoreRequest);
        return Response.status(Response.Status.OK).entity(result).build();
    }


    @GET
    @Path("{id}")
    @Operation(summary = "Get purchase data")
    @APIResponse(responseCode = "200", description = "Purchase data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PurchaseDto.class)))
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        PurchaseDto purchaseDto = purchaseService.getPurchaseById(id);
        if(Objects.isNull(purchaseDto))
            return Response.status(NOT_FOUND).build();

        return Response.ok(purchaseDto).build();
    }

    @POST
    @Transactional
    @Operation(summary = "Insert purchase in the database")
    @APIResponse(responseCode = "200", description = "Not modified", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PurchaseDto.class)))
    @APIResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PurchaseDto.class)))
    @APIResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PurchaseDto.class)))
    @APIResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiBadRequestException.class)))
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPurchase(PurchaseRequest purchaseRequest) throws ApiBadRequestException {

        if (Objects.isNull(purchaseRequest)
                || Objects.isNull(purchaseRequest.getDatePurchase())
                || Objects.isNull(purchaseRequest.getStore()) || purchaseRequest.getStore().isEmpty()
                || Objects.isNull(purchaseRequest.getProduct()) || purchaseRequest.getProduct().isEmpty()
                || Objects.isNull(purchaseRequest.getCost())){
            throw new ApiBadRequestException(ConstantsWeb.INVALID_PURCHASE_REQUEST);
        }

        Long purchaseId = purchaseService.addPurchase(purchaseRequest);
        if(!Objects.isNull(purchaseId)){
            return Response.created(URI.create(ConstantsWeb.PATH_PURCHASES+purchaseId)).build();
        }
        return Response.status(BAD_REQUEST).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    @Operation(summary = "Update purchase in the database")
    @APIResponse(responseCode = "200", description = "Not modified", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PurchaseDto.class)))
    @APIResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PurchaseDto.class)))
    @APIResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PurchaseDto.class)))
    @APIResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiBadRequestException.class)))
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePurchase(PurchaseRequest purchaseRequest, @PathParam("id") Long id) throws ApiBadRequestException {

        if (Objects.isNull(purchaseRequest)
                || Objects.isNull(purchaseRequest.getDatePurchase())
                || Objects.isNull(purchaseRequest.getStore()) || purchaseRequest.getStore().isEmpty()
                || Objects.isNull(purchaseRequest.getProduct()) || purchaseRequest.getProduct().isEmpty()
                || Objects.isNull(purchaseRequest.getCost()) ){
            throw new ApiBadRequestException(ConstantsWeb.INVALID_PURCHASE_REQUEST);
        }

        if (Objects.isNull(id)){
            throw new ApiBadRequestException(ConstantsWeb.INVALID_PURCHASE_ID);
        }

        PurchaseDto purchaseDto = purchaseService.updatePurchase(purchaseRequest, id);

        if(Objects.isNull(purchaseDto))
            return Response.status(NOT_FOUND).build();

        return Response.ok(purchaseDto).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Operation(summary = "Delete purchase by id")
    @APIResponse(responseCode = "200", description = "Accepted")
    @APIResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PurchaseDto.class)))
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Long id){
        boolean deleted = purchaseService.deletePurchaseById(id);
        return deleted ? Response.noContent().build() :
                Response.status(NOT_FOUND).build();
    }
}