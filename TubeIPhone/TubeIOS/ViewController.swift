//
//  ViewController.swift
//  TubeIOS
//
//  Created by Anshul Ahluwalia on 11/15/19.
//  Copyright © 2019 Anshul Ahluwalia. All rights reserved.
//

import UIKit
import FirebaseDatabase
import FirebaseAuth

class ViewController: UIViewController {
    
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        return false
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        if Auth.auth().currentUser != nil {
            self.performSegue(withIdentifier: "mainToHome", sender: self)
        }
        // Do any additional setup after loading the view.
        User.ref = Database.database().reference()
        
        //ref.child("user").setValue(["username": "Anshul"])
        //ref.child("user").setValue(["password": "Gtech"])
        //ref.child("user").setValue(["GTID": "903486371"])
        //ref.child("user").setValue(["Classes": "MATH 1564, ENGL 1102, CS 1331, CS 2701"])
        
        User.ref?.child("user").observe(.value, with: { (DataSnapshot) in
            let usernames = DataSnapshot.value as? [String: Any]
            print(usernames)
            
        })
        User.ref?.child("user").observe(.childAdded, with: {(DataSnapshot) in
            let usernames = DataSnapshot.value as? [String: Any]
            print("Testing child added event listener")
        })
        
        
        //ref.child("user").childByAutoId().setValue(userDictionary)
    }
    @IBAction func loginButtonClicked(_ sender: Any) {
        self.performSegue(withIdentifier: "mainToLogin", sender: self)
    }
    
    @IBAction func signUpButtonClicked(_ sender: UIButton) {
        self.performSegue(withIdentifier: "mainToRegistration", sender: self)
    }
}

