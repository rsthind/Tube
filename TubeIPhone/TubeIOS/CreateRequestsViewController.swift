//
//  CreateRequestsViewController.swift
//  TubeIOS
//
//  Created by Anshul Ahluwalia on 12/20/19.
//  Copyright © 2019 Anshul Ahluwalia. All rights reserved.
//

import UIKit
import FirebaseAuth

class CreateRequestsViewController: UIViewController {

    @IBOutlet weak var className: UITextField!
    @IBOutlet weak var timeField: UITextField!
    @IBOutlet weak var classDescription: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    @IBAction func submitButtonPressed(_ sender: UIButton) {
        writeRequestToDatabase(className: className.text!, dateTime: timeField.text!, description: classDescription.text!, reqSelected: false, uidToUse: Auth.auth().currentUser!.uid)
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
