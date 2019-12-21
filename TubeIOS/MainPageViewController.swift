//
//  MainPageViewController.swift
//  TubeIOS
//
//  Created by Anshul Ahluwalia on 12/17/19.
//  Copyright © 2019 Anshul Ahluwalia. All rights reserved.
//

import UIKit
import FirebaseAuth
import FirebaseDatabase

class MainPageViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        let userID = Auth.auth().currentUser?.uid
        User.ref.child("user").child(userID!).observeSingleEvent(of: .value, with: { (snapshot) in
            let value = snapshot.value as? NSDictionary
            User.firstName = value?["firstName"] as? String ?? ""
            User.email = value?["email"] as? String ?? ""
            User.GTID = value?["GTID"] as? String ?? ""
            User.major = value?["major"] as? String ?? ""
            let separatedClasses = value?["classes"] as? String ?? ""
            User.classesList = separatedClasses.components(separatedBy: ", ")
            User.classesList.remove(at: User.classesList.count - 1)
            print(User.firstName)
            print(User.email)
            print(User.GTID)
            print(User.major)
            print(User.classesList)
        }) { (error) in
            print(error.localizedDescription)
        }
        // Do any additional setup after loading the view.
    }
    
    @IBAction func logoutClicked(_ sender: UIButton) {
        do {
            try Auth.auth().signOut()
        }
        catch let signOutError as NSError {
            print("Error signing out: %@", signOutError)
        }
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let initial = storyboard.instantiateInitialViewController()
        UIApplication.shared.keyWindow?.rootViewController = initial
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
