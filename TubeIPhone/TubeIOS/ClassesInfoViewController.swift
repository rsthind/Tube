//
//  ClassesInfoViewController.swift
//  TubeIOS
//
//  Created by Anshul Ahluwalia on 11/23/19.
//  Copyright © 2019 Anshul Ahluwalia. All rights reserved.
//

import UIKit

class ClassesInfoViewController: UIViewController {
    @IBOutlet weak var CS1331Switch: UISwitch!
    
    @IBOutlet weak var MATH1554Switch: UISwitch!
    
    @IBOutlet weak var ENGL1102Switch: UISwitch!
    
    @IBOutlet weak var INTA1200Switch: UISwitch!
    
    @IBOutlet weak var ECON2106Switch: UISwitch!
    
    @IBOutlet weak var CS2701Switch: UISwitch!
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    @IBAction func classesSubmitButtonPressed(_ sender: Any) {
        if (CS1331Switch.isOn) {
            User.classesList.append("CS 1331")
        }
        if (MATH1554Switch.isOn) {
            User.classesList.append("MATH 1554")
        }
        if (ENGL1102Switch.isOn) {
            User.classesList.append("ENGL 1102")
        }
        if (INTA1200Switch.isOn) {
            User.classesList.append("INTA 1200")
        }
        if (ECON2106Switch.isOn) {
            User.classesList.append("ECON 2106")
        }
        if (CS2701Switch.isOn) {
            User.classesList.append("CS 2701")
        }
        print("User classes list: ")
        print(User.classesList)
        User.userFieldsFilled = true
        writeToDatabase(firstName: User.firstName, GTID: User.GTID, email: User.email, major: User.major, password: User.password, classesList: User.classesList)
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
