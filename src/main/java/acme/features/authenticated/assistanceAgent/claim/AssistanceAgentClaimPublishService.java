
package acme.features.authenticated.assistanceAgent.claim;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claims.Claim;
import acme.entities.claims.ClaimStatus;
import acme.entities.claims.ClaimType;
import acme.entities.legs.Leg;
import acme.features.manager.leg.ManagerLegRepository;
import acme.realms.AssistanceAgent;

@GuiService
public class AssistanceAgentClaimPublishService extends AbstractGuiService<AssistanceAgent, Claim> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AssistanceAgentClaimRepository	repository;

	@Autowired
	private ManagerLegRepository			legRepo;


	// AbstractGuiService interface -------------------------------------------
	@Override
	public void authorise() {
		Claim claim;
		int claimId;

		claimId = super.getRequest().getData("id", int.class);
		claim = this.repository.getClaimById(claimId);

		boolean hasAuthority = claim != null && claim.getDraftMode() && claim.getAssistanceAgent().getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId() && super.getRequest().getPrincipal().hasRealmOfType(AssistanceAgent.class);

		if (super.getRequest().getMethod().equals("POST"))
			hasAuthority = hasAuthority && this.validatePostFields();

		super.getResponse().setAuthorised(hasAuthority);
	}

	private boolean validatePostFields() {
		return this.validateStatus() && this.validateLeg();
	}

	private boolean validateLeg() {
		Integer legId = super.getRequest().getData("selectedLeg", int.class);
		if (legId != null && legId != 0) {
			Leg leg = this.legRepo.getLegById(legId);
			if (leg == null || leg.getIsDraft() || leg.getScheduledArrival().after(MomentHelper.getCurrentMoment()))
				return false;
		}
		return true;
	}

	private boolean validateStatus() {
		String claimType = super.getRequest().getData("type", String.class);
		if (claimType != null && !claimType.equals("0"))
			try {
				ClaimType.valueOf(claimType);
			} catch (IllegalArgumentException e) {
				return false;
			}
		return true;
	}

	@Override
	public void load() {
		Claim claim;
		int claimId;

		claimId = super.getRequest().getData("id", int.class);
		claim = this.repository.getClaimById(claimId);

		super.getBuffer().addData(claim);
	}

	@Override
	public void bind(final Claim claim) {
		int legId;
		Leg leg;

		legId = super.getRequest().getData("selectedLeg", int.class);
		leg = this.legRepo.getLegById(legId);

		super.bindObject(claim, "email", "description", "type");

		claim.setLeg(leg);

	}

	@Override
	public void validate(final Claim claim) {
		boolean hasChanges = true;

		Claim claimOriginal = this.repository.getClaimById(claim.getId());

		if (claim.getEmail() != null && !claim.getEmail().equals(claimOriginal.getEmail()))
			hasChanges = false;
		if (claim.getDescription() != null && !claim.getDescription().equals(claimOriginal.getDescription()))
			hasChanges = false;
		if (claim.getType() != null && !claim.getType().equals(claimOriginal.getType()))
			hasChanges = false;
		if (claim.getLeg() != null && !claim.getLeg().equals(claimOriginal.getLeg()))
			hasChanges = false;

		super.state(hasChanges, "*", "javax.validation.constraints.mustUpdate-first.message");

	}

	@Override
	public void perform(final Claim claim) {
		claim.setDraftMode(false);
		this.repository.save(claim);
	}

	@Override
	public void unbind(final Claim claim) {
		Dataset dataset;

		SelectChoices typeChoices;
		typeChoices = SelectChoices.from(ClaimType.class, claim.getType());

		ClaimStatus status = claim.getStatus();

		SelectChoices legs;
		legs = SelectChoices.from(this.repository.getAllPublishedLegs(MomentHelper.getCurrentMoment()), "flightNumber", claim.getLeg());

		dataset = super.unbindObject(claim, "registrationMoment", "email", "description");
		Leg leg = this.repository.getLegIsByClaimId(claim.getId());
		dataset.put("status", status);
		dataset.put("draftMode", claim.getDraftMode());
		dataset.put("type", typeChoices);
		dataset.put("legs", legs);
		dataset.put("selectedLeg", legs.getSelected().getKey());
		super.getResponse().addData(dataset);
	}

}
