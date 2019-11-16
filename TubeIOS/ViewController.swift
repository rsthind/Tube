//
//  ViewController.swift
//  TubeIOS
//
//  Created by Anshul Ahluwalia on 11/15/19.
//  Copyright © 2019 Anshul Ahluwalia. All rights reserved.
//

import UIKit
import FirebaseDatabase

class ViewController: UIViewController {
    var ref: DatabaseReference!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        ref = Database.database().reference()
        var userDictionary: [String: String] = [:]
        userDictionary["username"] = "Anshul"
        userDictionary["password"] = "Gtech"
        userDictionary["GTID"] = "903486371"
        userDictionary["Classes"] = "MATH 1564, ENGL 1102, CS 1331, CS 2701"
        //ref.child("user").setValue(["username": "Anshul"])
        //ref.child("user").setValue(["password": "Gtech"])
        //ref.child("user").setValue(["GTID": "903486371"])
        //ref.child("user").setValue(["Classes": "MATH 1564, ENGL 1102, CS 1331, CS 2701"])
        ref.child("user").childByAutoId().setValue(userDictionary)
        
    }


}

