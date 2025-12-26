package com.probase.potzr.SmartBanking.controllers;


import com.probase.potzr.SmartBanking.models.requests.IssueCardRequest;
import com.probase.potzr.SmartBanking.models.responses.mc.IssueCardResponse;
import com.probase.potzr.SmartBanking.service.CardService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api/v1/card")
public class CardController {


    @Autowired
    private CardService cardService;

    @RequestMapping(value = "/issue-card", method = RequestMethod.POST)
    public ResponseEntity<IssueCardResponse> postIssueCard(IssueCardRequest issueCardRequest)
    {
        IssueCardResponse issueCardResponse = cardService.issueCard(issueCardRequest);
        return ResponseEntity.ok().body(issueCardResponse);
    }

}
